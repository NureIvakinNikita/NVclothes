package com.example.nvclothes;

import com.cloudinary.utils.ObjectUtils;
import com.example.nvclothes.entity.products.AccessoriesEntity;
import com.example.nvclothes.repository.interfaces.AccessoriesEntityRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import com.cloudinary.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@SpringBootApplication
public class NvClothesApplication {

	@Autowired
	private static AccessoriesEntityRepositoryInterface accessoriesRepository;
	public static void main(String[] args) {
		SpringApplication.run(NvClothesApplication.class, args);

		/*Optional<AccessoriesEntity> optionalEntity = accessoriesRepository.getAccessoriesEntityByProductIdAndAttribute(1L, "BRAND");
		optionalEntity = accessoriesRepository.getAccessoriesEntityByProductIdAndAttribute(1L, "NAME");
		AccessoriesEntity entity = optionalEntity.get();
		entity.setAttribute("BRAND");
		entity.setValue("Faded Future");
		accessoriesRepository.save(entity);*/
		/*Map config = new HashMap();
		String cloudName, api_key, api_secret;
		cloudName = System.getenv("CLOUD_NAME");
		api_key = System.getenv("API_KEY");
		api_secret = System.getenv("API_SECRET");
		config.put("cloud_name", cloudName);
		config.put("api_key", api_key);
		config.put("api_secret", api_secret);
		Cloudinary cloudinary = new Cloudinary(config);*/

		/*try {

			cloudinary.uploader().upload("https://images.asos-media.com/products/jeepers-peepers-matte-rectangle-sunglasses-in-blue/204236265-2?$n_640w$&wid=513&fit=constrain", ObjectUtils.asMap("public_id", "accessory_3"));
			cloudinary.uploader().upload("https://images.asos-media.com/products/new-era-9twenty-new-york-yankees-unisex-cap-in-black/204454558-1-black?$n_640w$&wid=513&fit=constrain", ObjectUtils.asMap("public_id", "accessory_4"));

		} catch (IOException exception) {
			System.out.println(exception.getMessage());
		}*/
		/*String url;
		url = cloudinary.url().transformation(new Transformation().width(255).height(380).crop("fill")).generate("hoodie_1");
		System.out.println(url);
		url = cloudinary.url().transformation(new Transformation().width(255).height(380).crop("fill")).generate("hoodie_2");
		System.out.println(url);
		url = cloudinary.url().transformation(new Transformation().width(255).height(380).crop("fill")).generate("hoodie_3");
		System.out.println(url);
		url = cloudinary.url().transformation(new Transformation().width(255).height(380).crop("fill")).generate("hoodie_4");
		System.out.println(url);

		url = cloudinary.url().transformation(new Transformation().width(255).height(380).crop("fill")).generate("trainers_1");
		System.out.println(url);
		url = cloudinary.url().transformation(new Transformation().width(255).height(380).crop("fill")).generate("trainers_2");
		System.out.println(url);
		url = cloudinary.url().transformation(new Transformation().width(255).height(380).crop("fill")).generate("trainers_3");
		System.out.println(url);
		url = cloudinary.url().transformation(new Transformation().width(255).height(380).crop("fill")).generate("trainers_4");
		System.out.println(url);*/

		/*url = cloudinary.url().transformation(new Transformation().width(255).height(380).crop("fill")).generate("trousers_1");
		System.out.println(url);
		url = cloudinary.url().transformation(new Transformation().width(255).height(380).crop("fill")).generate("trousers_2");
		System.out.println(url);
		url = cloudinary.url().transformation(new Transformation().width(255).height(380).crop("fill")).generate("trousers_3");
		System.out.println(url);
		url = cloudinary.url().transformation(new Transformation().width(255).height(380).crop("fill")).generate("trousers_4");
		System.out.println(url);

		url = cloudinary.url().transformation(new Transformation().width(255).height(380).crop("fill")).generate("t_shirt_1");
		System.out.println(url);
		url = cloudinary.url().transformation(new Transformation().width(255).height(380).crop("fill")).generate("t_shirt_2");
		System.out.println(url);
		url = cloudinary.url().transformation(new Transformation().width(255).height(380).crop("fill")).generate("t_shirt_3");
		System.out.println(url);
		url = cloudinary.url().transformation(new Transformation().width(255).height(380).crop("fill")).generate("t_shirt_4");
		System.out.println(url);

		url = cloudinary.url().transformation(new Transformation().width(255).height(380).crop("fill")).generate("accessory_1");
		System.out.println(url);
		url = cloudinary.url().transformation(new Transformation().width(255).height(380).crop("fill")).generate("accessory_2");
		System.out.println(url);
		url = cloudinary.url().transformation(new Transformation().width(255).height(380).crop("fill")).generate("accessory_3");
		System.out.println(url);
		url = cloudinary.url().transformation(new Transformation().width(255).height(380).crop("fill")).generate("accessory_4");
		System.out.println(url);*/
	}

	@Bean
	public CommandLineRunner run(ApplicationContext ctx){

		return (args -> {

			System.out.println("hello");
		});
	}

}
