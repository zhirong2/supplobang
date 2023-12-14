package supplobang.services.impl;

import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import supplobang.dto.CartItemDto;
import supplobang.entities.Cart;
import supplobang.entities.CartItem;
import supplobang.entities.Flavour;
import supplobang.entities.Product;
import supplobang.exceptions.CartNotFoundException;
import supplobang.repository.CartItemRepository;
import supplobang.repository.CartRepository;
import supplobang.services.CartService;
import supplobang.services.FlavourService;
import supplobang.services.ProductService;

@Service
@Transactional
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductService productService;
    private final FlavourService flavourService;
    
    public Cart getCartById(Long cart_id){
        return cartRepository.findById(cart_id)
                .orElseThrow(() -> new CartNotFoundException("unable to find cart for user"));
    }

    public Cart addItemIntoCart(Long cart_id, CartItemDto cartItemDto){
        Cart cart = getCartById(cart_id);
        Product product = productService.getProductById(cartItemDto.getProduct_id());
        Flavour flavour = flavourService.getFlavourById(cartItemDto.getFlavour_id());

        List<CartItem> items = cart.getCartItems();

        Optional<CartItem> existingItem = findExistingItem(items, cartItemDto);
        
        addOrUpdateItem(existingItem, cartItemDto, cart, product, flavour);

        return cartRepository.save(cart);
    }

    public Cart updateCart(Long cart_id, List<CartItemDto> updatedCartItemDtos) {
        Cart cart = getCartById(cart_id);
        List<CartItem> existingItems = cart.getCartItems();
    
        for (CartItemDto updatedItem : updatedCartItemDtos) {
            Product product = productService.getProductByIdOrNull(updatedItem.getProduct_id());
            Flavour flavour = flavourService.getFlavourByIdOrNull(updatedItem.getFlavour_id());
            Optional<CartItem> existingItem = findExistingItem(existingItems, updatedItem);
    
            if (product == null || flavour == null) {

            } else {
                // Validate quantity
                if (updatedItem.getQuantity() <= 0) {
                    // Handle invalid quantity
                    // Either throw an exception or handle it as needed
                } else {
                    addOrUpdateItem(existingItem, updatedItem, cart, product, flavour);
                }
            }
        }
        return cartRepository.save(cart);
    }
    
    private void addOrUpdateItem(Optional<CartItem> existingItem, CartItemDto cartItemDto,
                                 Cart cart, Product product, Flavour flavour) {
        int maxQuantity = flavour.getFlavourQuantity();
        
        if (existingItem.isPresent()) {
            CartItem existingCartItem = existingItem.get();
            int updatedQuantity = existingCartItem.getQuantity() + cartItemDto.getQuantity();

            if(updatedQuantity > maxQuantity){

                String message = String.format("Requested quantity of %d for '%s' with flavour '%s' exceeds available stock (%d). Adjusting to available stock.",
                    updatedQuantity, product.getProductName(), flavour.getFlavourName(), maxQuantity);

                throw new RuntimeException(message);

                updatedQuantity = maxQuantity;
            }

            existingCartItem.setQuantity(updatedQuantity);
            cartItemRepository.save(existingCartItem);
            
        } else {
            CartItem newCartItem = new CartItem();
            newCartItem.setCart(cart);
            newCartItem.setProduct(product);
            newCartItem.setFlavour(flavour);
            newCartItem.setQuantity(cartItemDto.getQuantity());
            cart.getCartItems().add(newCartItem);
            cartItemRepository.save(newCartItem);
        }
    }

    private Optional<CartItem> findExistingItem(List<CartItem> existingItems, CartItemDto cartItemDto){
        return existingItems.stream()
            .filter(item -> item.getProduct().getId() == (cartItemDto.getProduct_id()) &&
                            item.getFlavour().getId() == (cartItemDto.getFlavour_id()))
            .findFirst();
    }
}
