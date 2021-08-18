package net.lunalabs.central.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;




/*Bean(name="") 과 이 custom 설정을 같이 적용하는 방법을 못찾겠음*/

//@Configuration
@Slf4j
public class CustomBeanNaming implements BeanNameGenerator {

	
    private final AnnotationBeanNameGenerator defaultGenerator = new AnnotationBeanNameGenerator();

    private List<String> basePackages = new ArrayList<>();

    
	   
	@Override
	public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
		
		String beanName;
		
		
		//Service anootain일 경우 full package 명으로 Bean 이름이 결정
//		if(isService(definition)) {
//			
//
//            beanName = generateFullBeanName((AnnotatedBeanDefinition) definition);
//			
//		}
		
		
	      if(isTargetPackageBean(definition)) {
	    	  beanName = generateFullBeanName((AnnotatedBeanDefinition) definition);
	        }
		
		else {
			
			//beanName = defaultGenerator.generateBeanName(definition, registry);
			//beanName = definition.getBeanClassName();
			beanName =  this.defaultGenerator.generateBeanName(definition, registry);
		}
		
		
		log.trace("Registered Bean Name : " + beanName);
		
		return beanName; 
	}
	
	

    private String generateFullBeanName(final AnnotatedBeanDefinition definition) {
        // 패키지를 포함한 전체 이름을 반환
        return definition.getMetadata().getClassName();
    }
	
	
	
	
    private boolean isService(final BeanDefinition definition) {
    	
        if (definition instanceof AnnotatedBeanDefinition) {
            final Set<String> annotationTypes = ((AnnotatedBeanDefinition) definition).getMetadata()
                    .getAnnotationTypes();

            return annotationTypes.stream()
                    .filter(type -> type.equals(Service.class.getName()))
                    .findAny()
                    .isPresent();
        }
        return false;
    }
    
    
    public boolean addBasePackages(String path) {
        return this.basePackages.add(path);
    }
	
    private boolean isTargetPackageBean(BeanDefinition definition) {
        String beanClassName = definition.getBeanClassName();
        return basePackages.stream().anyMatch(beanClassName::startsWith);
    }
    
    
    
}
