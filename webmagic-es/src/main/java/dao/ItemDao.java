package dao;

import bean.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import java.util.List;

public interface ItemDao extends ElasticsearchCrudRepository<Item,Integer> {
    List<Item> findByTitleAndContent(String title,String content);
}
