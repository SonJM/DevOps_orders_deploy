package com.practice.ordersystem.domain.Item.Controller;

import com.practice.ordersystem.domain.Item.DTO.ItemReqDto;
import com.practice.ordersystem.domain.Item.DTO.ItemResDto;
import com.practice.ordersystem.domain.Item.DTO.ItemSearchDto;
import com.practice.ordersystem.domain.Item.Item;
import com.practice.ordersystem.domain.Item.Service.ItemService;
import com.practice.ordersystem.domain.common.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
public class ItemController {
    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/item/create")
    public ResponseEntity<ResponseDto> itemCreate(ItemReqDto itemReqDto){
        Item item = itemService.create(itemReqDto);
        return new ResponseEntity<>
                (new ResponseDto(HttpStatus.CREATED, "item successfully create", item.getId())
                        , HttpStatus.CREATED);
    }

    @GetMapping("/items")
    public ResponseEntity<List<ItemResDto>> getItems(ItemSearchDto itemSearchDto, Pageable pageable){
        List<ItemResDto> itemResDtos = itemService.findAll(itemSearchDto, pageable);
        return new ResponseEntity<>(itemResDtos, HttpStatus.OK);
    }

    @GetMapping("/item/{id}/image")
    public ResponseEntity<Resource> getImage(@PathVariable Long id){
        Resource resource = itemService.getImage(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/item/{id}/update")
    public ResponseEntity<ResponseDto> itemUpdate(@PathVariable Long id, ItemReqDto itemReqDto){
        Item item = itemService.update(id, itemReqDto);
        return new ResponseEntity<>(new ResponseDto(HttpStatus.OK, "item successfully updated", item.getId()), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/item/{id}/delete")
    public ResponseEntity<ResponseDto> itemDelete(@PathVariable Long id){
        Item item = itemService.delete(id);
        return new ResponseEntity<>(new ResponseDto(HttpStatus.OK, "item successfully deleted", item.getId()), HttpStatus.OK);
    }
}
