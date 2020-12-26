package com.hp.springboot.common.factory;

import java.io.IOException;

import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

/**
 * 描述： 作者：20025894 时间：2020-12-21
 */
public class YmlPropertySourceFactory implements PropertySourceFactory {

	@Override
	public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
		return new YamlPropertySourceLoader().load(name, resource.getResource()).get(0);
	}

}
