package jp.co.musako.domain.service;

import jp.co.musako.domain.model.User;
import jp.co.musako.domain.model.UserHistory;
import jp.co.musako.domain.repository.UserHistoryRepository;
import jp.co.musako.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserHistoryRepository userHistoryRepository;

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User create(User user) {
        LocalDateTime now = LocalDateTime.now();
        user.setCreateTimestamp(now);
        User currentUser = userRepository.save(user);

        // 変更履歴を登録
        UserHistory userHistory = new UserHistory();
        userHistory.setUserId(currentUser.getId());
        userHistory.setMail(currentUser.getMail());
        userHistory.setGender(currentUser.getGender());
        userHistory.setBirthdate(currentUser.getBirthdate());
        userHistory.setPassword(currentUser.getPassword());
        userHistory.setCreateTimestamp(now);
        userHistory.setNote("create user");
        userHistoryRepository.save(userHistory);

        return currentUser;
    }

    @Transactional
    public User update(Long id, User user) {

        User currentUser = userRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        LocalDateTime now = LocalDateTime.now();

        // 変更内容を登録
        currentUser.setMail(user.getMail());
        currentUser.setGender(user.getGender());
        currentUser.setBirthdate(user.getBirthdate());
        currentUser.setCreateTimestamp(now);
        userRepository.save(currentUser);

        // 変更履歴を登録
        UserHistory userHistory = new UserHistory();
        userHistory.setUserId(currentUser.getId());
        userHistory.setMail(currentUser.getMail());
        userHistory.setGender(currentUser.getGender());
        userHistory.setBirthdate(currentUser.getBirthdate());
        userHistory.setPassword(currentUser.getPassword());
        userHistory.setCreateTimestamp(now);
        userHistory.setNote("edit user");
        userHistoryRepository.save(userHistory);

        return currentUser;
    }

    @Transactional
    public void delete(Long id) {

        User currentUser = userRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        LocalDateTime now = LocalDateTime.now();

        UserHistory userHistory = new UserHistory();
        userHistory.setUserId(currentUser.getId());
        userHistory.setMail(currentUser.getMail());
        userHistory.setGender(currentUser.getGender());
        userHistory.setBirthdate(currentUser.getBirthdate());
        userHistory.setPassword(currentUser.getPassword());
        userHistory.setCreateTimestamp(now);
        userHistory.setNote("delete user");
        userHistoryRepository.save(userHistory);

        userRepository.deleteById(id);
    }

}
