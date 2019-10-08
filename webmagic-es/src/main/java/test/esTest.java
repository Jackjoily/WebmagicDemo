package test;

import bean.Item;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.ItemService;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class esTest {
    @Autowired
    private ItemService itemService;

    @Autowired
    private ElasticsearchTemplate template;

    //创建索引和映射
    @Test
    public void createIndex() {
        this.template.createIndex(Item.class);
        this.template.putMapping(Item.class);
    }

    @Test
    public void save() {
        Item item = new Item();
        item.setId(100);
        item.setTitle("SpringData es");
        item.setContent("今天我们使用springdata完成搜索功能");
        this.itemService.save(item);
    }
    //新增

    //修改

    @Test
    public void testUpdate() {
        Item item = new Item();
        item.setId(100);
        item.setTitle("SpringData es");
        item.setContent("今天我们使用springdata完成搜索功能,实现job搜索功能");
        this.itemService.save(item);
    }
    //删除

    @Test
    public void testDelete() {
        Item item = new Item();
        item.setId(100);
        this.itemService.delete(item);
    }

    //批量保存
    @Test
    public void testSaveAll() {
        //创建集合，封装数据
        List<Item> list = new ArrayList<>();

        //封装数据
        for (int i = 1; i < 100; i++) {
            Item item = new Item();
            item.setId(i);
            item.setTitle("SpringData es   " + i);
            item.setContent("今天我们使用springdata完成搜索功能    " + i);
            list.add(item);
        }
        this.itemService.saveAll(list);
    }
//查询所有数据

    @Test
    public void testFindAll(){
        Iterable<Item> items=this.itemService.findAll();
        for (Item item : items) {
            System.out.println(item);
        }
    }

    //分页查询
    @Test
    public void testFindByPage(){
        Page<Item> items= this.itemService.findByPage(1,30);
        for (Item item : items.getContent()) {
            System.out.println(item);
        }
    }

    //复杂查询
    //根据title和content进行查询
    @Test
    public void testfindByTitleAndContent(){
        List<Item>  list=this.itemService.findByTitleAndContent("ES","job");
        for (Item item : list) {
            System.out.println(item);
        }
    }

}
