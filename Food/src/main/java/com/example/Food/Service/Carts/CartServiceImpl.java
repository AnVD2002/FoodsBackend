package com.example.Food.Service.Carts;

import com.example.Food.DTO.Request.ClientRequest.CartRequest;
import com.example.Food.DTO.Request.ClientRequest.UpdateCartItemRequest;
import com.example.Food.Entity.Cart.CartItems;
import com.example.Food.Entity.Cart.Carts;
import com.example.Food.Entity.Food.FoodDetails;
import com.example.Food.Entity.Food.Foods;
import com.example.Food.Entity.User.User;
import com.example.Food.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private FoodsRepository foodsRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private FoodDetailsRepository foodDetailsRepository;
    @Autowired
    private FoodDetailsPropertyDetailsRepository foodDetailsPropertyDetailsRepository;
    @Autowired
    private CartItemsRepository cartItemsRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<?> addToCart(CartRequest cartRequest) {
        Optional<User> user = userRepository.FindByName(cartRequest.getUserName());
        if (user.isEmpty()) {
            return new ResponseEntity<>("not exist user", HttpStatus.NOT_FOUND);
        }
        Optional<Foods> food = foodsRepository.findById(cartRequest.getFoodID());
        if (food.isEmpty()) {
            return new ResponseEntity<>("not exist food", HttpStatus.NOT_FOUND);
        }
        List<Integer> foodDetailIDs = foodDetailsPropertyDetailsRepository.findFoodDetailIDsByFoodIDAndPropertyDetailIDs(cartRequest.getFoodID(), cartRequest.getPropertyDetailID());
        if (foodDetailIDs.isEmpty()) {
            return new ResponseEntity<>("not exist foodDetail", HttpStatus.NOT_FOUND);
        }
        Optional<FoodDetails> foodDetail = foodDetailsRepository.findById(foodDetailIDs.get(0));
        Optional<Carts> cart = cartRepository.findCartByUserName(cartRequest.getUserName());
        if (foodDetail.isEmpty()) {
            return new ResponseEntity<>("not exist foodDetail", HttpStatus.NOT_FOUND);
        }
        double sum =0;
        if (cart.isPresent()) {
            Optional<CartItems> cartItemOpt = cartItemsRepository.findCartItemByFoodDetailID(foodDetail.get().getFoodDetailID(), cart.get().getCartID());
            if (cartItemOpt.isPresent()) {
                cartItemOpt.get().setQuantity(cartRequest.getQuantity()+cartItemOpt.get().getQuantity());
                cartItemsRepository.save(cartItemOpt.get());
                for (CartItems cartItem : cart.get().getCartItems()) {
                    if(cartItem.getQuantity()>foodDetail.get().getQuantity()){
                        return new ResponseEntity<>("not enough foodDetail", HttpStatus.NOT_FOUND);
                    }
                    sum += cartItem.getPrice()*cartItem.getQuantity();
                }
                cart.get().setTotal(sum);
                cartRepository.save(cart.get());
                return new ResponseEntity<>("save completed", HttpStatus.OK);
            }
            CartItems newCartItem = new CartItems();
            newCartItem.setFoodDetail(foodDetail.get());
            newCartItem.setQuantity(cartRequest.getQuantity());
            newCartItem.setPrice(foodDetail.get().getPrice());
            newCartItem.setDescription(food.get().getDescription());
            newCartItem.setImg(food.get().getImage());
            newCartItem.setFoodDetailName(foodDetail.get().getFoodDetailName());
            newCartItem.setFoodDetailID(foodDetail.get().getFoodDetailID());
            newCartItem.setCart(cart.get());
            cartItemsRepository.save(newCartItem);
            cart.get().setUpdateAt(LocalDate.now());
            cart.get().getCartItems().add(newCartItem);
            for (CartItems cartItem : cart.get().getCartItems()) {
                sum += cartItem.getPrice()*cartItem.getQuantity();
            }
            cart.get().setTotal(sum);
            cartRepository.save(cart.get());
            return new ResponseEntity<>("save completed", HttpStatus.OK);

        }
        Carts newCart = new Carts();
        newCart.setUser(user.get());
        newCart.setCreateAt(LocalDate.now());
        newCart.setCartItems(new ArrayList<>());
        cartRepository.save(newCart);

        CartItems newCartItem = new CartItems();
        newCartItem.setFoodDetail(foodDetail.get());
        newCartItem.setQuantity(cartRequest.getQuantity());
        newCartItem.setCart(newCart);
        newCartItem.setDescription(food.get().getDescription());
        newCartItem.setImg(food.get().getImage());
        newCartItem.setPrice(foodDetail.get().getPrice());
        newCartItem.setFoodDetailName(foodDetail.get().getFoodDetailName());
        newCartItem.setFoodDetailID(foodDetail.get().getFoodDetailID());
        cartItemsRepository.save(newCartItem);
        sum+=foodDetail.get().getPrice()*foodDetail.get().getQuantity();
        newCart.setTotal(sum*cartRequest.getQuantity());
        newCart.getCartItems().add(newCartItem);
        cartRepository.save(newCart);
        return new ResponseEntity<>("save new cart completed", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAllCartItems(String userName) {
        Optional<Carts> cart = cartRepository.findCartByUserName(userName);
        if (cart.isEmpty()) {
            return new ResponseEntity<>("not exist cart", HttpStatus.NOT_FOUND);
        }
        List<CartItems> cartItems = cart.get().getCartItems();
        return new ResponseEntity<>(cartItems, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> removeCartItem(Integer itemID) {
        Optional<CartItems> cartItemOptional = cartItemsRepository.findById(itemID);
        if (cartItemOptional.isEmpty()) {
            return new ResponseEntity<>("CartItem does not exist", HttpStatus.NOT_FOUND);
        }
        CartItems cartItem = cartItemOptional.get();
        Carts cart = cartItem.getCart();

        if (cartItem.getQuantity() > 1) {
            cartItem.setQuantity(cartItem.getQuantity() - 1);
            cartItemsRepository.save(cartItem);
        } else {
            cart.setTotal(0.0);
            cartItemsRepository.delete(cartItem);
        }

        return new ResponseEntity<>("Cart item updated or removed, but cart not deleted", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updateCartItem(UpdateCartItemRequest request) {
        if(request.getCartItemID()==null){
            return new ResponseEntity<>("itemID is null", HttpStatus.BAD_REQUEST);
        }
        if(request.getQuantity()==null){
            return new ResponseEntity<>("quantity is null", HttpStatus.BAD_REQUEST);
        }
        Optional<CartItems> cartItemOptional = cartItemsRepository.findById(request.getCartItemID());
        if (cartItemOptional.isEmpty()) {
            return new ResponseEntity<>("CartItem does not exist", HttpStatus.NOT_FOUND);
        }
        CartItems cartItem = cartItemOptional.get();
        Optional<Carts> cart = cartRepository.findById(cartItem.getCart().getCartID());
        if (cart.isEmpty()) {
            return new ResponseEntity<>("CartItem does not exist", HttpStatus.NOT_FOUND);
        }
        cartItem.setQuantity(request.getQuantity());
        cartItemsRepository.save(cartItem);

        double total = 0;
        for(CartItems cartItems: cart.get().getCartItems()){
            total += cartItems.getPrice()*cartItem.getQuantity();
        }
        cart.get().setTotal(total);
        cartRepository.save(cart.get());
        return new ResponseEntity<>("updated",HttpStatus.OK);
    }
}

