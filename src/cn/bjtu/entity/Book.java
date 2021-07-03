package cn.bjtu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author chancey
 * @create 2020-07-17 18:02
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Book {
    //链式编程+流式计算

    private Integer id;
    private String bookName;
    private Double price;

    public static void main(String[] args) {
        Book book = new Book();
//        book.setId(1).setBookName("111").setPrice(12.0);
    }
}
