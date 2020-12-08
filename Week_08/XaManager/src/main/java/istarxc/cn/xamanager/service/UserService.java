package istarxc.cn.xamanager.service;

import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.apache.shardingsphere.transaction.core.TransactionTypeHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
     @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    @ShardingTransactionType(TransactionType.XA)
    public TransactionType insert() {
        return jdbcTemplate.execute("INSERT INTO user (id,name,identity_id) VALUES (?, ?, ?)", (PreparedStatementCallback<TransactionType>) preparedStatement -> {
            preparedStatement.execute();
            return TransactionTypeHolder.get();
        });
    }
}
