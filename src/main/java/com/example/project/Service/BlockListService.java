package com.example.project.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.project.Model.BlockList;
import com.example.project.Model.User;
import com.example.project.Repository.BlockListRepository;

import net.minidev.json.JSONObject;


@Service
public class BlockListService {

	private final BlockListRepository blockListRepository;

	@Autowired
	public BlockListService(BlockListRepository blockListRepository) {
		this.blockListRepository = blockListRepository;
	}

	public List<BlockList> getBlockLists() {
		return blockListRepository.findAll();
	}
	
	 public List<BlockList> getUserBlockList(User userId) {
	        return blockListRepository.findUserBlockList(userId);
	    }
	 
	 public List<BlockList> findBlockedList(User userId) {
         return blockListRepository.findBlockedList(userId);
     }

	public JSONObject blockUser(BlockList blockList) {
        JSONObject jsonBody= new JSONObject();
	    BlockList blocks = blockListRepository.findBlockList(blockList.getBlockedUser(), blockList.getBlockedBy());
	            if (blocks!=null) {
                    jsonBody.clear();
                    jsonBody.put("Error Message", "User is already Blocked");
                }else {
                    blockListRepository.save(blockList);
                    jsonBody.clear();
                    jsonBody.put("Success Message", "User Blocked");
                }
        
	  
        return jsonBody;
	    
	}
	
	public JSONObject unblockUser(BlockList blockList) {
	    JSONObject jsonBody= new JSONObject();
	    
	    BlockList block= blockListRepository.findBlockList(blockList.getBlockedUser(), blockList.getBlockedBy());
	    if (block!=null) {
            jsonBody.clear();  
            blockListRepository.deleteById(block.getBlockedUserId());
            jsonBody.put("Success Message", "User Unblocked");
        }else {
            jsonBody.clear();
            jsonBody.put("Error Message", "User is already Blocked");
        }
	  
	    return jsonBody;
	}
	
	public JSONObject unblock(int blockId) {
        JSONObject jsonBody= new JSONObject();
        
        blockListRepository.deleteById(blockId);
        
        jsonBody.put("Success Message", "User Unblocked");
        return jsonBody;
    }
}
