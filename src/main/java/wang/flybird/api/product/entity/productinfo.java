package wang.flybird.api.product.entity;

import java.util.List;

public class productinfo<MultipartFile> {
   private String id;
   private String name;
   private String description;
   private String publisherId;
   private String publisher;
   private String publishtime;
   private String commentcount;
   private String readcount;
   
   private List<MultipartFile> imageList;
   private List<MultipartFile> videoList;
   
}
