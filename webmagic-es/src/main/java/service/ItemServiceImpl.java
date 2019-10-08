package service;

import bean.Item;
import dao.ItemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemDao dao;

    @Override
    public void save(Item item) {
        this.dao.save(item);

    }

    @Override
    public void delete(Item item) {
        this.dao.delete(item);
    }

    @Override
    public void saveAll(List<Item> list) {
        this.dao.saveAll(list);
    }

    @Override
    public Iterable<Item> findAll() {
        Iterable<Item> items = this.dao.findAll();
        return items;
    }

    @Override
    public Page<Item> findByPage(int page, int rows) {
        return this.dao.findAll(PageRequest.of(page, rows));
    }

    @Override
    public List<Item> findByTitleAndContent(String title, String content) {
      List<Item> list=  this.dao.findByTitleAndContent( title,  content);
     return list;
    }
}
