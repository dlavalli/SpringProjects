package com.lavalliere.daniel.spring.vaultclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.vault.core.VaultKeyValueOperationsSupport.KeyValueBackend;
import org.springframework.vault.core.VaultSysOperations;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.core.VaultTransitOperations;
import org.springframework.vault.support.VaultMount;
import org.springframework.vault.support.VaultResponse;

@SpringBootApplication
public class VaultClientApplication implements CommandLineRunner {

	@Autowired
	private VaultTemplate vaultTemplate;

	/*
		Documentation:
		https://developer.hashicorp.com/vault/docs/concepts

	    Hashicorp server setup (locally installed). Manually starting
        1- vault server --dev --dev-root-token-id="00000000-0000-0000-0000-000000000000"

	    Using vault CLI to create a key/value pair with value
		2- set two environment variables to point the Vault CLI to the Vault endpoint and provide an authentication token.
		   In Powershell:
		   $env:VAULT_TOKEN="00000000-0000-0000-0000-000000000000"
		   $env:VAULT_ADDR="http://127.0.0.1:8200"
		3- Now you can store a configuration key-value pairs inside Vault:
		   vault kv put secret/github github.oauth2.key=foobar
	 */

	/*
		Spring Cloud Vault uses VaultOperations to interact with Vault.
		Properties from Vault get mapped to MyConfiguration for type-safe access.
		@EnableConfigurationProperties(MyConfiguration.class) enables configuration property mapping
		and registers a MyConfiguration bean.

		Application includes a main() method that autowires an instance of MyConfiguration.
	 */

	@Override
	public void run(String... args) throws Exception {
		// You usually would not print a secret to stdout
		// https://docs.spring.io/spring-vault/reference/api/java/org/springframework/vault/core/VaultTemplate.html
		// Returns the operations interface to interact with the Vault Key/Value backend.
		VaultResponse response = vaultTemplate
			.opsForKeyValue("secret", KeyValueBackend.KV_2)  // Versioned api
			.get("github");


		//========================================================================
		// Lets Extract the key value
		//========================================================================
		System.out.println("Value of github.oauth2.key");
		System.out.println("-------------------------------");
		System.out.println(response.getData().get("github.oauth2.key"));
		System.out.println("-------------------------------");
		System.out.println();


		//========================================================================
		// Let's encrypt some data using the Transit backend.
		//========================================================================
		VaultTransitOperations transitOperations = vaultTemplate.opsForTransit();

		// We need to setup transit first (assuming you didn't set up it yet).
		VaultSysOperations sysOperations = vaultTemplate.opsForSys();

		if (!sysOperations.getMounts().containsKey("transit/")) {

			sysOperations.mount("transit", VaultMount.create("transit"));

			transitOperations.createKey("foo-key");
		}


		//========================================================================
		// Lets Encrypt a plain-text value
		//========================================================================
		String ciphertext = transitOperations.encrypt("foo-key", "Secure message");

		System.out.println("Encrypted value");
		System.out.println("-------------------------------");
		System.out.println(ciphertext);
		System.out.println("-------------------------------");
		System.out.println();


		//========================================================================
		// Lets Decrypt it
		//========================================================================
		String plaintext = transitOperations.decrypt("foo-key", ciphertext);
		System.out.println("Decrypted value");
		System.out.println("-------------------------------");
		System.out.println(plaintext);
		System.out.println("-------------------------------");
		System.out.println();


		/*
			Value of github.oauth2.key
			-------------------------------
			foobar
			-------------------------------

			Encrypted value
			-------------------------------
			vault:v1:wF4+DqdnusaNbhpMGcbrF6qzNqEzFn4iD+qoyi0TjagqkqIrIMDRby3h
			-------------------------------

			Decrypted value
			-------------------------------
			Secure message
			-------------------------------
		 */
	}

	public static void main(String[] args) {
		SpringApplication.run(VaultClientApplication.class, args);
	}
}
