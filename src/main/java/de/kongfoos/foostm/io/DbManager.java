package de.kongfoos.foostm.io;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.inject.Singleton;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Component;

@Component
@Singleton
public class DbManager {

	@Autowired
	private ConfigurableApplicationContext appContext;

	private Map<String, EntityManagerFactory> factoryMap = new HashMap<String, EntityManagerFactory>();
	private Map<String, DataSource> dataMap = new HashMap<String, DataSource>();

	private DataSource createDataSource(String dbName) {
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		dataSource.setDriverClass(org.h2.Driver.class);
		dataSource
				.setUrl("jdbc:h2:~/foosTM/" + dbName + ";AUTO_RECONNECT=TRUE");
		dataSource.setUsername("sa");
		dataSource.setPassword("");
		return dataSource;
	}

	private LocalContainerEntityManagerFactoryBean createEntityManagerFactoryBean(
			String dbName, String[] packagesToScan) {
		DataSource dataSource = createDataSource(dbName);

		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource);
		em.setPackagesToScan(packagesToScan);

		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);

		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect",
				"org.hibernate.dialect.H2Dialect");
		properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
		em.setJpaProperties(properties);

		em.afterPropertiesSet();

		factoryMap.put(dbName, em.getObject());
		dataMap.put(dbName, dataSource);

		return em;
	}

	public void openDatabase(String dbName, String[] packagesToScan) {
		if (!factoryMap.containsKey(dbName)) {
			// create factory
			LocalContainerEntityManagerFactoryBean emf = createEntityManagerFactoryBean(
					dbName, packagesToScan);

			// register factory to context
			ConfigurableListableBeanFactory registry = appContext
					.getBeanFactory();
			registry.registerSingleton("emf." + dbName, emf);
		}
	}

	public EntityManagerFactory getEntityManagerFactory(String dbName) {
		return factoryMap.get(dbName);
	}

	public DataSource getDataSource(String dbName) {
		return dataMap.get(dbName);
	}

	public void startWebServer(String dbName) {
		try {
			Server.startWebServer(getDataSource(dbName).getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
