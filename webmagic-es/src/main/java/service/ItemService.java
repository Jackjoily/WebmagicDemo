package service;

import bean.Item;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ItemService  {

    //新增方法
    void save(Item item);

    void delete(Item item);

    void saveAll(List<Item> list);

    Iterable<Item>   findAll();

    Page<Item> findByPage(int i, int i1);

    List<Item> findByTitleAndContent(String title, String content);
}
