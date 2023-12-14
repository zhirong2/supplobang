package supplobang.services.impl;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import supplobang.repository.CartItemRepository;
import supplobang.services.CartItemService;

@Service
@Transactional
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService{

}
