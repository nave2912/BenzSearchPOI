package com.benz.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.benz.handle.APIFilter;

import net.sf.ehcache.config.CacheConfiguration;

@Configuration
@EnableCaching
public class APICacheConfig extends CachingConfigurerSupport {

	@Bean
	public APIFilter aPIFilter() {
		return new APIFilter();
	}

	@Bean
	public net.sf.ehcache.CacheManager ehCacheManager() {
		CacheConfiguration tenMinuteCache = new CacheConfiguration();
		tenMinuteCache.setName("ten-minute-cache");
		tenMinuteCache.setMemoryStoreEvictionPolicy("LRU");
		tenMinuteCache.setMaxEntriesLocalHeap(1000);
		tenMinuteCache.setTimeToLiveSeconds(600);
		net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
		config.addCache(tenMinuteCache);
		return net.sf.ehcache.CacheManager.newInstance(config);
	}

	@Bean
	@Override
	public CacheManager cacheManager() {
		return new EhCacheCacheManager(ehCacheManager());
	}

}