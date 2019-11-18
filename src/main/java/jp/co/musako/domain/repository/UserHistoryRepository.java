package jp.co.musako.domain.repository;

import jp.co.musako.domain.model.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {
}
