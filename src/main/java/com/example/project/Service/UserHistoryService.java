package com.example.project.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project.Model.UserHistory;
import com.example.project.Repository.UserHistoryRepository;

@Service
public class UserHistoryService {

	private final UserHistoryRepository historyRepository;

	@Autowired
	public UserHistoryService(UserHistoryRepository historyRepository) {
		this.historyRepository= historyRepository;
	}

    public List<UserHistory> getPasswordHistory() {
    	return historyRepository.findAll();
    }

	public void addNewPasswordHistory(UserHistory history) {
		historyRepository.save(history);
	}

	public void deletePasswordHistory(int historyId) {
		boolean exists= historyRepository.existsById(historyId);
		if (!exists) {
			throw new IllegalStateException("history with id "+ historyId + "does not exist");
		}
		historyRepository.deleteById(historyId);
	}

	@Transactional
	public void updatePasswordHistory(int historyId, UserHistory passHistory) {
		UserHistory history= historyRepository.findById(historyId).orElseThrow(()-> new IllegalStateException("history with id "+ historyId + " does not exist"));

		if (passHistory.getChangedDate()!=null ) {
			history.setChangedDate(passHistory.getChangedDate());
		}

		if (passHistory.getChangedPassword()!=null && passHistory.getChangedPassword().length()>0) {
			history.setChangedPassword(passHistory.getChangedPassword());
		}

		if (passHistory.getUserId()!=null) {
			history.setUserId(passHistory.getUserId());
		}

	}
}
