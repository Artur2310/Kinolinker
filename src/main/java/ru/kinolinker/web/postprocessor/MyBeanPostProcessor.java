package ru.kinolinker.web.postprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

	
	public Object postProcessAfterInitialization(Object object, String name) throws BeansException {
		System.err.println("postProcessAfterInitialization(): " + object);
		return object;
	}

	
	public Object postProcessBeforeInitialization(Object object, String name) throws BeansException {
		return object;
	}

}
