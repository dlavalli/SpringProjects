package com.lavalliere.daniel.spring.vaultclient;

import java.util.Map;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;

import org.springframework.context.annotation.PropertySource;
import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.support.Versioned;
import org.springframework.vault.core.VaultTemplate;

// From Hashicorp vault example
// https://github.com/hashicorp/vault-examples/blob/main/examples/_quick-start/java/Example.java
// https://docs.spring.io/spring-vault/reference/introduction/getting-started.html
@SpringBootApplication
public class VaultClientApplication implements CommandLineRunner {

	@Value("${vault.token}")
	private String vaultToken;

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(VaultClientApplication.class, args);
		context.close();
	}

	@Override
	public void run(String... strings) throws Exception {
		VaultEndpoint vaultEndpoint = new VaultEndpoint();

		vaultEndpoint.setHost("127.0.0.1");
		vaultEndpoint.setPort(8200);
		vaultEndpoint.setScheme("http");

		// Authenticate
		VaultTemplate vaultTemplate = new VaultTemplate(
			vaultEndpoint,
			new TokenAuthentication(vaultToken));

		// Write a secret
		Map<String, String> data = new HashMap<>();
		data.put("password", "Hashi123");

		Versioned.Metadata createResponse = vaultTemplate
			.opsForVersionedKeyValue("secret")
			.put("my-secret-password", data);

		System.out.println("Secret written successfully.");

		// Read a secret
		Versioned<Map<String, Object>> readResponse = vaultTemplate
			.opsForVersionedKeyValue("secret")
			.get("my-secret-password");

		String password = "";
		if (readResponse != null && readResponse.hasData()) {
			password = (String) readResponse.getData().get("password");
		}

		if (!password.equals("Hashi123")) {
			throw new Exception("Unexpected password");
		}

		System.out.println("Access granted!");
	}
}
