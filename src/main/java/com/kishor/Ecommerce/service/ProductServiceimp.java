package com.kishor.Ecommerce.service;

import com.kishor.Ecommerce.project.exceptions.ApiException;
import com.kishor.Ecommerce.project.exceptions.RescourceNotFoundException;
import com.kishor.Ecommerce.project.model.Caterogy;
import com.kishor.Ecommerce.project.model.Product;
import com.kishor.Ecommerce.project.payload.ProductDTO;
import com.kishor.Ecommerce.project.payload.ProductResponse;
import com.kishor.Ecommerce.project.repository.CaterogyRepository;
import com.kishor.Ecommerce.project.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceimp implements ProductService{
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CaterogyRepository caterogyRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String path;
    @Override
    public ProductDTO addProduct(Long caterogyID, ProductDTO productDTO) {
            Caterogy caterogy=caterogyRepository.findById(caterogyID)
                .orElseThrow(()-> new RescourceNotFoundException("Caterogy","CaterogyID",caterogyID));
            boolean isProductNotPresent=true;
            List<Product>productList=caterogy.getProductList();
            for(Product value :productList){
                if(value.getProductName().equals(productDTO.getProductName())){
                    isProductNotPresent=false;
                    break;
                }

            }
            if(isProductNotPresent) {
                Product product = modelMapper.map(productDTO, Product.class);
                product.setCaterogy(caterogy);
                if (product.getImage() == null) {
                    product.setImage("product.png");
                }
                if (product.getPrice() <= 0) {
                    throw new ApiException("Invalid price");
                }
                if (product.getDiscount() <= 0 || product.getDiscount() > 100) {
                    throw new ApiException("Invalid discount");
                }
                double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
                product.setSpecialprice(specialPrice);
                Product savedProduct = productRepository.save(product);
                return modelMapper.map(savedProduct, ProductDTO.class);
            }else{
                throw new ApiException("product all exist");
            }
    }
//    public ProductDTO addProduct(Long categoryId, ProductDTO productDTO) {
//
//        Caterogy category = caterogyReposistory.findById(categoryId)
//                .orElseThrow(() -> new RescourceNotFoundException("Category", "id", categoryId));
//
//        Product product = modelMapper.map(productDTO, Product.class);
//        product.setCaterogy(category);
//
//        if (product.getImage() == null || product.getImage().isBlank()) {
//            product.setImage("default.png");
//        }
//        product.setSpecialprice(calculateSpecialPrice(product.getPrice(), product.getDiscount()));
//
//        Product savedProduct = productReposistory.save(product);
//
//        return modelMapper.map(savedProduct, ProductDTO.class);
//    }
//    private double calculateSpecialPrice(double price, double discount) {
//        return price - ((discount / 100) * price);
//    }

    @Override
    public ProductResponse getAllProduct(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder=sortOrder.equalsIgnoreCase("asc")
                ?Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();
        Pageable pageDetails= PageRequest.of(pageNumber,pageSize,sortByAndOrder);
        Page<Product> pageProducts= productRepository.findAll(pageDetails);
        List<Product>products=pageProducts.getContent();
        List<ProductDTO>productDTOS=products.stream()
                .map(product ->modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
        if (products.isEmpty()){
            throw new ApiException("not product is not present");
        }
        ProductResponse productResponse=new ProductResponse();
        productResponse.setContent(productDTOS);
        productResponse.setPageSize(pageProducts.getSize());
        productResponse.setTotalElements(productResponse.getTotalElements());
        productResponse.setTotalPages(pageProducts.getTotalPages());
        productResponse.setLastPage(productResponse.isLastPage());
        return productResponse;
    }

    @Override
    public ProductResponse searchByCategory(Long caterogyID, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Caterogy category = caterogyRepository.findById(caterogyID)
                .orElseThrow(() -> new RescourceNotFoundException("Category", "categoryId", caterogyID));
        Sort sortByAndOrder=sortBy.equalsIgnoreCase("asc")
                ?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageDetails=PageRequest.of(pageNumber,pageSize,sortByAndOrder);
        Page<Product>pageProducts= productRepository.findByCaterogyOrderByPriceAsc(category,pageDetails);
        List<Product> products = pageProducts.getContent();

        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        if (products.isEmpty()){
            throw new ApiException(category.getCaterogyName()+"category does not have any products");
        }

        ProductResponse response = new ProductResponse();
        response.setContent(productDTOS);
        response.setPageSize(pageProducts.getSize());
        response.setTotalPages(pageProducts.getTotalPages());
        response.setTotalElements(pageProducts.getTotalElements());
        response.setLastPage(pageProducts.isLast());
        return response;
    }

    @Override
    public ProductResponse searchProductByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder=sortBy.equalsIgnoreCase("Asc")
                ?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageDetails=PageRequest.of(pageNumber,pageSize,sortByAndOrder);
        Page<Product>pageProducts= productRepository.findByProductNameLikeIgnoreCase("%"+keyword+"%",pageDetails);
        List<Product> products =pageProducts.getContent();

        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        if (products.isEmpty()){
            throw new ApiException("product not matched based on this keyword"+keyword);
        }
        ProductResponse response = new ProductResponse();
        response.setContent(productDTOS);
        response.setPageSize(pageProducts.getSize());
        response.setTotalPages(pageProducts.getTotalPages());
        response.setTotalElements(pageProducts.getTotalElements());
        response.setLastPage(pageProducts.isLast());
        return response;
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO, Long productId) {
        Product productFromDB= productRepository.findById(productId)
                .orElseThrow(()->new RescourceNotFoundException("product","productId",productId));
        productFromDB.setProductName(productDTO.getProductName());
        productFromDB.setDescription(productDTO.getDescription());
        productFromDB.setQuantity(productDTO.getQuantity());
        productFromDB.setPrice(productDTO.getPrice());
        productFromDB.setDiscount(productDTO.getDiscount());
        double specialPrice=productDTO.getPrice()-((productDTO.getDiscount()*0.01)*productDTO.getPrice());
        productFromDB.setSpecialprice(specialPrice);
     Product savedProduct= productRepository.save(productFromDB);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
      Product product=  productRepository.findById(productId)
              .orElseThrow(()->new RescourceNotFoundException("product","productId",productId));
        productRepository.delete(product);
      ProductDTO productDTO=modelMapper.map(product, ProductDTO.class);
        return productDTO;
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
        //get the product From DB;
        Product productFromDB= productRepository.findById(productId).orElseThrow(()->new RescourceNotFoundException("product","productId",productId));
        //upload image to server
        //get the filename of upload image
        String fileName=fileService.uploadImage(path,image);
        //updating the new file name to the product
        productFromDB.setImage(fileName);
        //save the product from db
      Product updateProduct=  productRepository.save(productFromDB);
        //return dto after mapping to dto
        return modelMapper.map(updateProduct, ProductDTO.class);
    }

}
