package com.example.Food.Service.Carts;

import com.example.Food.DTO.Request.CartRequest;
import com.example.Food.Entity.Cart.CartItems;
import com.example.Food.Entity.Cart.Carts;
import com.example.Food.Entity.Food.FoodDetails;
import com.example.Food.Entity.Food.FoodDetailsPropertyDetails;
import com.example.Food.Entity.Food.Foods;
import com.example.Food.Entity.User.User;
import com.example.Food.Repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService implements ImplCartService {
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
        Optional<User> user = userRepository.findById((cartRequest.getUserID()));
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
        Optional<Carts> cart = cartRepository.findCartByUserID(cartRequest.getUserID());
        if (foodDetail.isEmpty()) {
            return new ResponseEntity<>("not exist foodDetail", HttpStatus.NOT_FOUND);
        }
        if (cart.isPresent()) {
            Optional<CartItems> cartItem = cartItemsRepository.findCartItemByFoodDetailID(foodDetail.get().getFoodDetailID());
            if (cartItem.isPresent()) {
                cartItem.get().setQuantity(cartRequest.getQuantity());
                cartItemsRepository.save(cartItem.get());
                return new ResponseEntity<>("save completed", HttpStatus.OK);
            }
            CartItems newCartItem = new CartItems();
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
            cartRepository.save(cart.get());
            return new ResponseEntity<>("save completed", HttpStatus.OK);

        }
        Carts newCart = new Carts();
        newCart.setUser(user.get());
        newCart.setCreateAt(LocalDate.now());
        newCart.setCartItems(new ArrayList<>());
        cartRepository.save(newCart);

        CartItems newCartItem = new CartItems();
        newCartItem.setQuantity(cartRequest.getQuantity());
        newCartItem.setCart(newCart);
        newCartItem.setDescription(food.get().getDescription());
        newCartItem.setImg(food.get().getImage());
        newCartItem.setPrice(foodDetail.get().getPrice());
        newCartItem.setFoodDetailName(foodDetail.get().getFoodDetailName());
        newCartItem.setFoodDetailID(foodDetail.get().getFoodDetailID());
        cartItemsRepository.save(newCartItem);


        newCart.getCartItems().add(newCartItem);
        cartRepository.save(newCart);
        return new ResponseEntity<>("save new cart completed", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAllCartItems(int userID) {
        Optional<Carts> cart = cartRepository.findCartByUserID(userID);
        if (cart.isEmpty()) {
            return new ResponseEntity<>("not exist cart", HttpStatus.NOT_FOUND);
        }
        List<CartItems> cartItems = cart.get().getCartItems();
        return new ResponseEntity<>(cartItems, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> removeCartItem(int itemID) {
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
            cartItemsRepository.delete(cartItem);
        }

        return new ResponseEntity<>("Cart item updated or removed, but cart not deleted", HttpStatus.OK);
    }
}

