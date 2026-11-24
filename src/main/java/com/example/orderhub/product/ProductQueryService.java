package com.example.orderhub.product;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class ProductQueryService {
    private final ProductSearchRepository searchRepository;
    public ProductQueryService(ProductSearchRepository searchRepository) { this.searchRepository=searchRepository; }
    public List<ProductEntity> search(String keyword,List<String> tags) {
        List<ProductEntity> products=searchRepository.searchByName(keyword==null?"":keyword);
        if(tags.isEmpty()) return products;
        return products.stream().filter(p->tags.contains(p.getTag())).toList();
    }
}