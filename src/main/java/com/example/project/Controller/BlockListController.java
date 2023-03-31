package com.example.project.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.Model.BlockList;
import com.example.project.Model.User;
import com.example.project.Service.BlockListService;

import net.minidev.json.JSONObject;

@RestController
@RequestMapping(path = "api/blockList")
public class BlockListController {

    private final BlockListService BlockListService;

    @Autowired
    public BlockListController(BlockListService BlockListService) {
        this.BlockListService= BlockListService;
    }

    @GetMapping("/showUserBlockList/{userId}")
    public List<BlockList> getUserBlockList(User userId) {
        return BlockListService.getUserBlockList(userId);
    }
    
    @GetMapping("/showBlockedList/{userId}")
    public List<BlockList> getBlockedList(User userId) {
        return BlockListService.findBlockedList(userId);
    }
    
    @PostMapping("/blockUser")
    public JSONObject blockUser(@RequestBody BlockList blockList) {
        return BlockListService.blockUser(blockList);
    }
    
    @PostMapping("/unblockUser")
    public JSONObject unblockUser(@RequestBody BlockList blockList) {
        return BlockListService.unblockUser(blockList);
    }
}
    
/*
 * @DeleteMapping("/unblockUser/{blockId}")
 * public JSONObject unblockUser(@PathVariable("blockId") int blockId) {
 * return BlockListService.unblockUser(blockId);
 * }
 * }
 */
