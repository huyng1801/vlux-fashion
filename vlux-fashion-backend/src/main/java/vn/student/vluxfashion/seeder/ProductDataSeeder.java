package vn.student.vluxfashion.seeder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import vn.student.vluxfashion.model.Brand;
import vn.student.vluxfashion.model.Category;
import vn.student.vluxfashion.model.Gender;
import vn.student.vluxfashion.model.Product;
import vn.student.vluxfashion.model.ProductColor;
import vn.student.vluxfashion.model.ProductColorImage;
import vn.student.vluxfashion.model.ProductSize;
import vn.student.vluxfashion.model.SubCategory;
import vn.student.vluxfashion.repository.BrandRepository;
import vn.student.vluxfashion.repository.CategoryRepository;
import vn.student.vluxfashion.repository.ProductColorImageRepository;
import vn.student.vluxfashion.repository.ProductColorRepository;
import vn.student.vluxfashion.repository.ProductRepository;
import vn.student.vluxfashion.repository.ProductSizeRepository;
import vn.student.vluxfashion.repository.SubCategoryRepository;

import java.util.Date;

@Component
@Configuration
public class ProductDataSeeder {

    private static final Logger logger = LoggerFactory.getLogger(ProductDataSeeder.class);

    @Bean
    CommandLineRunner seedProducts(ProductRepository productRepository, BrandRepository brandRepository, SubCategoryRepository subCategoryRepository, CategoryRepository categoryRepository, ProductColorRepository productColorRepository, ProductSizeRepository productSizeRepository, ProductColorImageRepository productColorImageRepository) {
        return args -> {
            if (productRepository.count() == 0) { // Prevents duplicate entries

                // Retrieve or create brands
                Brand calvinKlein = findOrCreateBrand(brandRepository, "Calvin Klein", "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/brand/thpp_ck.svg");

                Category aoCategory = findOrCreateCategory(categoryRepository, "Áo");
                Category quanCategory = findOrCreateCategory(categoryRepository, "Quần");

                // Retrieve or create subcategories
                SubCategory menShirts = findOrCreateSubCategory(subCategoryRepository, "Áo sơ mi nam", aoCategory, Gender.MALE);
                SubCategory womenShirts = findOrCreateSubCategory(subCategoryRepository, "Áo sơ mi nữ", aoCategory, Gender.FEMALE);
                SubCategory thunNam = findOrCreateSubCategory(subCategoryRepository, "Áo thun nam", aoCategory, Gender.MALE);
                SubCategory thunNu = findOrCreateSubCategory(subCategoryRepository, "Áo thun nữ", aoCategory, Gender.FEMALE);
                SubCategory poloNam = findOrCreateSubCategory(subCategoryRepository, "Áo polo nam", aoCategory, Gender.MALE);
                SubCategory poloNu = findOrCreateSubCategory(subCategoryRepository, "Áo polo nữ", aoCategory, Gender.FEMALE);
                SubCategory menJeans = findOrCreateSubCategory(subCategoryRepository, "Quần jeans nam", quanCategory, Gender.MALE);
                SubCategory womenJeans = findOrCreateSubCategory(subCategoryRepository, "Quần jeans nữ", quanCategory, Gender.FEMALE);
                SubCategory daiNam = findOrCreateSubCategory(subCategoryRepository, "Quần dài nam", quanCategory, Gender.MALE);
                SubCategory daiNu = findOrCreateSubCategory(subCategoryRepository, "Quần dài nữ", quanCategory, Gender.FEMALE);
                SubCategory shortNam = findOrCreateSubCategory(subCategoryRepository, "Short nam", quanCategory, Gender.MALE);
                SubCategory shortNu = findOrCreateSubCategory(subCategoryRepository, "Short nữ", quanCategory, Gender.FEMALE);
                
                // Add products to each subcategory with realistic names and prices
                Product p1 = addProduct(productRepository, calvinKlein, menShirts, "Áo Sơ Mi Nam Camp Denim Shirt", 4099000);
       
                String[] p1Images = {
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p1/c25_01_j3257481a4_mo-st-b1_3.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p1/c25_01_j3257481a4_mo-st-d1_3.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p1/c25_01_j3257481a4_mo-st-f1_3.webp"
                };

                String[] p1Sizes = {"M"};
                Integer[] p1StockQuantities = {10};

                addProductColor(
                    productColorRepository,
                    productColorImageRepository,
                    productSizeRepository,
                    p1,
                    "DENIM MEDIUM",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p1/c25_01_j3257481a4_mo-st-f2_3.webp",
                    p1Images,
                    p1Sizes,
                    p1StockQuantities
                );
                                
                Product p2 = addProduct(productRepository, calvinKlein, menShirts, "Áo Sơ Mi Nam Slim Fit", 1979000);

                String[] p2Images = {
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p2/j319065-beh-2_1.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p2/j319065-beh-3_1.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p2/j319065-beh-4_1.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p2/j319065-beh-5_1.webp"
                };

                String[] p2Sizes = {"S", "M", "L", "XL"};
                Integer[] p2StockQuantities = {10, 10, 10, 10};

                addProductColor(
                    productColorRepository,
                    productColorImageRepository,
                    productSizeRepository,
                    p2,
                    "CK BLACK",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p2/c25_01_j3257481a4_mo-st-f2_3.webp",
                    p2Images,
                    p2Sizes,
                    p2StockQuantities
                );

                Product p3 = addProduct(productRepository, calvinKlein, menShirts, "Áo Sơ Mi Nam Relaxed Fit", 4099000);

                String[] p3Images = {
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p3/j3255581a4_mo-st-b1_1.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p3/j3255581a4_mo-st-d1_1.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p3/j3255581a4_mo-st-f1_1.webp"
                };

                String[] p3Sizes = {"S", "M", "L"};
                Integer[] p3StockQuantities = {0, 10, 10};

                addProductColor(
                    productColorRepository,
                    productColorImageRepository,
                    productSizeRepository,
                    p3,
                    "117 MID BLUE",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p3/j3255581a4_mo-st-f2_1.webp",
                    p3Images,
                    p3Sizes,
                    p3StockQuantities
                );

                Product p4 = addProduct(productRepository, calvinKlein, womenShirts, "Áo Sơ Mi Không Tay Nữ Women Shirt", 1935000);

                
                String[] p4Images = {
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p4/sisley_24p_5wmelq06n_101_b_full_pdp_v_phgoulcce8xnp6nx.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p4/sisley_24p_5wmelq06n_101_d1_full_pdp_v_lzpiwoksx4wbybms.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p4/sisley_24p_5wmelq06n_101_p_full_pdp_v_rlwrwikyqv2asgcx.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p4/sisley_24p_5wmelq06n_101_s_full_pdp_v_18lu6smncuo6i4py.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p4/sisley_24p_5wmelq06n_101_fs_full_pdp_v_jlcn4sem8usuevxf.webp"
                };

                String[] p4Sizes = {"XS", "S", "M"};
                Integer[] p4StockQuantities = {10, 10, 10};

                addProductColor(
                    productColorRepository,
                    productColorImageRepository,
                    productSizeRepository,
                    p4,
                    "WHITE",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p4/sisley_24p_5wmelq06n_101_f_full_pdp_v_32dlr1dbqczz8lvs.webp",
                    p4Images,
                    p4Sizes,
                    p4StockQuantities
                );

                Product p5 = addProduct(productRepository, calvinKlein, womenShirts, "Áo Sơ Mi Nữ - Ls Cate Shirt", 2799000);
                String[] p5Images = {
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p5/w2yh41waf10-g011-2_ghdolvnglvrnuzts.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p5/w2yh41waf10-g011-3_tqg9pw50gyj0rd4b.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p5/w2yh41waf10-g011-4_5jksqe4maxsq1kdr.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p5/w2yh41waf10-g011-5_yxkblj76tusdcfij.webp"
                };
                String[] p5Sizes = {"S", "M", "L"};
                Integer[] p5StockQuantities = {10, 10, 10};

                addProductColor(
                    productColorRepository,
                    productColorImageRepository,
                    productSizeRepository,
                    p5,
                    "PURE WHITE",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p5/w2yh41waf10-g011-1_fxnpxgs1o7tqvwjs.webp",
                    p5Images,
                    p5Sizes,
                    p5StockQuantities
                );
                Product p6 = addProduct(productRepository, calvinKlein, womenShirts, "Áo Sơ Mi Nữ Saturn 1", 1190000);
                String[] p6Images = {
                  
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p6/214449_bm_3y_y4hr5jqyoljwfs2o.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p6/214449_bm_4y_dz1tuo5p1dwpqvfs.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p6/214449_bm_5y_gtyvh67pyyaqklcn.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p6/214449_bm_6y_y5y1rseowkzf1z7t.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p6/214449_bm_1yf_thnejmwzildsrdtz.webp"
                };
                String[] p6Sizes = {"XS/S"};
                Integer[] p6StockQuantities = {10};

                addProductColor(
                    productColorRepository,
                    productColorImageRepository,
                    productSizeRepository,
                    p6,
                    "BRIGHT MULTICOLOR",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p6/214449_bm_2y_vlqquhcrrb8eso5w.webp",
                    p6Images,
                    p6Sizes,
                    p6StockQuantities
                );
                Product p7 = addProduct(productRepository, calvinKlein, thunNam, "Áo Thun Nam Slim Fit", 1609000);
                String[] p7Images = {
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p7/j324497yaf-2_s34i6nq91emjghph.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p7/j324497yaf-3_qmjwo9fmh7cjjiqf.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p7/j324497yaf-4_1qmhoo99ozq3xzcn.webp"
                };
                String[] p7Sizes = {"S", "M", "L", "XL"};
                Integer[] p7StockQuantities = {10, 10, 10, 10};

                addProductColor(
                    productColorRepository,
                    productColorImageRepository,
                    productSizeRepository,
                    p7,
                    "BRIGHT WHITE",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p7/j324497yaf-1_yky8tj4eailykr3k.webp",
                    p7Images,
                    p7Sizes,
                    p7StockQuantities
                );
                Product p8 = addProduct(productRepository, calvinKlein, thunNam, "Áo Thun Nam Oversize Fit", 2999000);
                String[] p8Images = {
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p8/j326354-beh-2_3.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p8/j326354-beh-3_3.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p8/j326354-beh-4_3.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p8/j326354-beh-5_3.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p8/j326354-beh-6_3.webp"
                };
                String[] p8Sizes = {"S", "M"};
                Integer[] p8StockQuantities = {0, 10};

                addProductColor(
                    productColorRepository,
                    productColorImageRepository,
                    productSizeRepository,
                    p8,
                    "CK BLACK",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p8/j326354-beh-1_3.webp",
                    p8Images,
                    p8Sizes,
                    p8StockQuantities
                );
                Product p9 = addProduct(productRepository, calvinKlein, thunNam, "Áo Thun Nam Slim Fit", 2299000);
                String[] p9Images = {
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p9/j325512beh_mo-st-f1.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p9/j325512beh_mo-st-d1.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p9/j325512beh_mo-st-b1.webp"
                };
                String[] p9Sizes = {"S", "M", "L"};
                Integer[] p9StockQuantities = {10, 10, 10};

                addProductColor(
                    productColorRepository,
                    productColorImageRepository,
                    productSizeRepository,
                    p9,
                    "CK BLACK",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p9/j325512beh_mo-st-f2.webp",
                    p9Images,
                    p9Sizes,
                    p9StockQuantities
                );
                Product p10 = addProduct(productRepository, calvinKlein, thunNu, "Áo Thun Nữ Sheer Long Sleeve Top", 2299000);
                String[] p10Images = {
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p10/j224335rae_mo-st-d1_2.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p10/j224335rae_mo-st-f1_2.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p10/j224335rae_mo-st-b1_2.webp"
                };
                String[] p10Sizes = {"S", "M"};
                Integer[] p10StockQuantities = {10, 10};

                addProductColor(
                    productColorRepository,
                    productColorImageRepository,
                    productSizeRepository,
                    p10,
                    "PALE KHAKI",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p10/j224335rae_mo-st-f2_2.webp",
                    p10Images,
                    p10Sizes,
                    p10StockQuantities
                );
                Product p11 = addProduct(productRepository, calvinKlein, thunNu, "Áo Thun Nữ Regular Fit", 1899000);
                String[] p11CkblackImages = {
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p11/ckblack/j218885-beh-2_1.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p11/ckblack/j218885-beh-3_1.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p11/ckblack/j218885-beh-4_1.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p11/ckblack/j218885-beh-5_1.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p11/ckblack/j218885-beh-6_1.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p11/ckblack/j218885-beh-7_1.webp"
                };
                String[] p11CkblackSizes = {"XS", "S", "M"};
                Integer[] p11CkblackStockQuantities = {10, 0, 10};

                addProductColor(
                    productColorRepository,
                    productColorImageRepository,
                    productSizeRepository,
                    p11,
                    "CK BLACK",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p11/ckblack/j218885-beh-1_1.webp",
                    p11CkblackImages,
                    p11CkblackSizes,
                    p11CkblackStockQuantities
                );
                String[] p11SepiaroseImages = {
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p11/sepiarose/j218885tf6_mo-st-f1_1.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p11/sepiarose/j218885tf6_mo-st-d1_1.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p11/sepiarose/j218885tf6_mo-st-b1_1.webp"
                };
                String[] p11SepiaroseSizes = {"XS", "S", "M"};
                Integer[] p11SepiaroseStockQuantities = {10, 10, 10};

                addProductColor(
                    productColorRepository,
                    productColorImageRepository,
                    productSizeRepository,
                    p11,
                    "SEPIA ROSE",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p11/sepiarose/j218885tf6_mo-st-f2_1.webp",
                    p11SepiaroseImages,
                    p11SepiaroseSizes,
                    p11SepiaroseStockQuantities
                );

                Product p12 = addProduct(productRepository, calvinKlein, thunNu, "Áo Thun Nữ Slim Fit", 4299000);
                String[] p12Images = {
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p12/j222958-beh-2_jljydapxoirlf1iw.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p12/j222958-beh-3_qhoekaoqzwqu0rp9.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p12/j222958-beh-4_m8l52e1ugw7lowkd.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p12/j222958-beh-5_c2lgawoixawhasvr.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p12/j222958-beh-6_8a7aq1rpcqmc0s8f.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p12/j222958-beh-7_sashc8bigavfumhn.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p12/j222958-beh-8_uutdnvetugachkeu.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p12/j222958-beh-9_vgon0ne5hx2dkvup.webp"
                };
                String[] p12Sizes = {"S", "M"};
                Integer[] p12StockQuantities = {10, 10};

                addProductColor(
                    productColorRepository,
                    productColorImageRepository,
                    productSizeRepository,
                    p12,
                    "CK BLACK",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p12/j222958-beh-1_iyw18vltl0yrxluw.webp",
                    p12Images,
                    p12Sizes,
                    p12StockQuantities
                );
                
                Product p13 = addProduct(productRepository, calvinKlein, poloNam, "Áo Polo Nam", 2499000);
                String[] p13Images = {
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p13/j325055beh-2_s27f0zvpwt2tcdug.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p13/j325055beh-3_5117mjw2shrqf9bq.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p13/j325055beh-4_hhraimbvvpyniurx.webp"
                };
                String[] p13Sizes = {"S", "M"};
                Integer[] p13StockQuantities = {10, 10};

                addProductColor(
                    productColorRepository,
                    productColorImageRepository,
                    productSizeRepository,
                    p13,
                    "CK BLACK",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p13/j325055beh-1_tjj2xev4yxzl5tm8.webp",
                    p13Images,
                    p13Sizes,
                    p13StockQuantities
                );
                
                Product p14 = addProduct(productRepository, calvinKlein, poloNam, "Áo Polo Nam Slim Fit", 1749300);
                String[] p14CkBlackImages = {
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p14/CKBLACK/j324476beh-2_0k2zrewmtoe20dw4.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p14/CKBLACK/j324476beh-3_5w4kfvfjlmooendt.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p14/CKBLACK/j324476beh-4_wuydzama69nrvjrv.webp"
                };
                String[] p14CkBlackSizes = {"XS", "S", "M", "L", "XL"};
                Integer[] p14CkBlackStockQuantities = {10, 10, 10, 10, 10};

                addProductColor(
                    productColorRepository,
                    productColorImageRepository,
                    productSizeRepository,
                    p14,
                    "CK BLACK",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p14/CKBLACK/j324476beh-1_s7dzopnlm9cbxthm.webp",
                    p14CkBlackImages,
                    p14CkBlackSizes,
                    p14CkBlackStockQuantities
                );
                String[] p14PlazaTaupeImages = {
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p14/PLAZATAUPE/j324476ped-2_fj2xx5truakgrln5.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p14/PLAZATAUPE/j324476ped-3_1vudmk9cjfnkriy4.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p14/PLAZATAUPE/j324476ped-4_jxw7xnrshufgtk1x.webp"
                };
                String[] p14PlazaTaupeSizes = {"S", "M"};
                Integer[] p14PlazaTaupeStockQuantities = {10, 10};

                addProductColor(
                    productColorRepository,
                    productColorImageRepository,
                    productSizeRepository,
                    p14,
                    "PLAZA TAUPE",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p14/PLAZATAUPE/j324476ped-1_wqy4ld7f2fmro9le.webp",
                    p14PlazaTaupeImages,
                    p14PlazaTaupeSizes,
                    p14PlazaTaupeStockQuantities
                );

                String[] p14ThymeImages = {
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p14/THYME/j324476llp-2_syrsijezxqrjuaq4.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p14/THYME/j324476llp-3_8ajtgiy1fdojrlft.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p14/THYME/j324476llp-4_x8vxl3tiyslanlms.webp"
                };
                String[] p14ThymeSizes = {"M"};
                Integer[] p14ThymeStockQuantities = {10};

                addProductColor(
                    productColorRepository,
                    productColorImageRepository,
                    productSizeRepository,
                    p14,
                    "PLAZA TAUPE",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p14/THYME/j324476llp-1_xnehxnprwqvpa0wx.webp",
                    p14ThymeImages,
                    p14ThymeSizes,
                    p14ThymeStockQuantities
                );
                Product p15 = addProduct(productRepository, calvinKlein, poloNam, "Áo Polo Nam Relaxed Fit", 2299000);
                String[] p15Images = {
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p15/j30j325428_0gp_alternate2_2.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p15/j30j325428_0gp_alternate3_2.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p15/j30j325428_0gp_alternate4_2.webp",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p15/j30j325428_0gp_main_2.webp"
                };
                String[] p15Sizes = {"S", "M", "L"};
                Integer[] p15StockQuantities = {10, 10, 10};

                addProductColor(
                    productColorRepository,
                    productColorImageRepository,
                    productSizeRepository,
                    p15,
                    "WARP LOGO AOP",
                    "https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/product_color/p15/j30j325428_0gp_alternate1_2.webp",
                    p15Images,
                    p15Sizes,
                    p15StockQuantities
                );
                Product p16 = addProduct(productRepository, calvinKlein, poloNu, "Áo Polo Nữ", 4299000);
                Product p17 = addProduct(productRepository, calvinKlein, poloNu, "Áo Polo Nữ Cropped Fit", 3299000);
                Product p18 = addProduct(productRepository, calvinKlein, poloNu, "Áo Polo Nữ Slim Fit", 2899000);
                
                Product p19 = addProduct(productRepository, calvinKlein, menJeans, "Quần Jeans Nam", 4239200);
                Product p20 = addProduct(productRepository, calvinKlein, menJeans, "Quần Jeans Nam Dài Slim", 1399000);
                Product p21 = addProduct(productRepository, calvinKlein, menJeans, "Quần Jeans Nam Body Fit", 5699000);
                Product p22 = addProduct(productRepository, calvinKlein, womenJeans, "Quần Jeans Nữ Dài Boyfriend", 10495000);
                Product p23 = addProduct(productRepository, calvinKlein, womenJeans, "Quần Jeans Nữ Dài Boyfriend", 1049500);
                Product p24 = addProduct(productRepository, calvinKlein, womenJeans, "Quần Jeans Nữ Dài Baggy", 1049500);
                
                Product p25 = addProduct(productRepository, calvinKlein, daiNam, "Quần Khaki Nam Dài Slim Fit Chinos In Light Cotton", 1590000);
                Product p26 = addProduct(productRepository, calvinKlein, daiNam, "Quần Dài Milan", 1399000);
                Product p27 = addProduct(productRepository, calvinKlein, daiNam, "Quần Dài Nam Five Pocket Carrot Fit Trousers", 2390000);
                Product p28 = addProduct(productRepository, calvinKlein, daiNu, "Quần Dài Vải Nữ - Marzia Jogger", 4599000);
                Product p29 = addProduct(productRepository, calvinKlein, daiNu, "Quần Thun Dài Nữ-Blair Wide Leg Pant Asia Fit", 829000);
                Product p30 = addProduct(productRepository, calvinKlein, daiNu, "Quần Dài Nữ Whisper Trouser", 1799000);
                
                Product p31 = addProduct(productRepository, calvinKlein, shortNam, "Quần Ngắn Nam Fashion Fit", 1590600);
                Product p32 = addProduct(productRepository, calvinKlein, shortNam, "Quần Ngắn Nam Straight Fit", 3299000);
                Product p33 = addProduct(productRepository, calvinKlein, shortNam, "Quần Ngắn Nam Trouser Short", 3599000);
                Product p34 = addProduct(productRepository, calvinKlein, shortNu, "Quần Ngắn Nữ Sweatshorts Fit", 3299000);
                Product p35 = addProduct(productRepository, calvinKlein, shortNu, "Quần Ngắn Nữ Bermuda Fit", 3599000);
                Product p36 = addProduct(productRepository, calvinKlein, shortNu, "Quần Ngắn Nữ Relaxed Fit", 3599000);
                logger.info("Products seeded successfully!");
            }
        };
    }

    private Brand findOrCreateBrand(BrandRepository brandRepository, String brandName, String imageUrl) {
        return brandRepository.findByBrandName(brandName)
            .orElseGet(() -> {
                Brand brand = new Brand();
                brand.setBrandName(brandName);
                brand.setImageUrl(imageUrl);
                brand.setCreatedAt(new Date());
                brand.setUpdatedAt(new Date());
                return brandRepository.save(brand);
            });
    }

    private SubCategory findOrCreateSubCategory(SubCategoryRepository subCategoryRepository, String subCategoryName, Category category, Gender gender) {
        return subCategoryRepository.findBySubCategoryName(subCategoryName)
            .orElseGet(() -> {
                SubCategory subCategory = new SubCategory();
                subCategory.setSubCategoryName(subCategoryName);
                subCategory.setCategory(category);
                subCategory.setCreatedAt(new Date());
                subCategory.setUpdatedAt(new Date());
                subCategory.setGender(gender);
                return subCategoryRepository.save(subCategory);
            });
    }

    private Category findOrCreateCategory(CategoryRepository categoryRepository, String categoryName) {
        return categoryRepository.findByCategoryName(categoryName)
            .orElseGet(() -> {
                Category category = new Category();
                category.setCategoryName(categoryName);
                category.setCreatedAt(new Date());
                category.setUpdatedAt(new Date());
                return categoryRepository.save(category);
            });
    }

    private Product addProduct(ProductRepository productRepository, Brand brand, SubCategory subCategory, String productName, long price) {
        Product product = new Product();
        product.setProductName(productName);
        product.setOriginalPrice(price);
        product.setUnitPrice(price);
        product.setIsVisible(true);
        product.setBrand(brand);
        product.setSubCategory(subCategory);
        product.setCreatedAt(new Date());
        product.setUpdatedAt(new Date());
        return productRepository.save(product);
    }


    // Modified addProductColor to include sizes and stock quantities
    private void addProductColor(
        ProductColorRepository productColorRepository,
        ProductColorImageRepository productColorImageRepository,
        ProductSizeRepository productSizeRepository,
        Product product,
        String colorName,
        String imageUrl,
        String[] imageUrls,
        String[] sizeValues,
        Integer[] stockQuantities
    ) {
        ProductColor productColor = new ProductColor();
        productColor.setProduct(product);
        productColor.setColorName(colorName);
        productColor.setImageUrl(imageUrl);
        productColor = productColorRepository.save(productColor);  // Save the ProductColor first
        
        // Add images to the product color
        for (String imgUrl : imageUrls) {
            addProductColorImage(productColorImageRepository, productColor, imgUrl);
        }
        
        // Add sizes and stock quantities to the product color
        for (int i = 0; i < sizeValues.length; i++) {
            addProductSize(productSizeRepository, productColor, sizeValues[i], stockQuantities[i]);
        }
        
    }

    // Adding images to the product color
    private void addProductColorImage(ProductColorImageRepository productColorImageRepository, ProductColor productColor, String imageUrl) {
        ProductColorImage productColorImage = new ProductColorImage();
        productColorImage.setProductColor(productColor);
        productColorImage.setImageUrl(imageUrl);
        productColorImageRepository.save(productColorImage);
    }

    // Adding sizes to the product color
    private void addProductSize(ProductSizeRepository productSizeRepository, ProductColor productColor, String sizeValue, Integer stockQuantity) {
        ProductSize productSize = new ProductSize();
        productSize.setSizeValue(sizeValue);
        productSize.setStockQuantity(stockQuantity);
        productSize.setProductColor(productColor);
        productSizeRepository.save(productSize);
    }


}
