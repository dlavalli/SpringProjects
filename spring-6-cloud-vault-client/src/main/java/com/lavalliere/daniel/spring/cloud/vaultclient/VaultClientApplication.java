package com.lavalliere.daniel.spring.cloud.vaultclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.vault.core.VaultKeyValueOperationsSupport.KeyValueBackend;
import org.springframework.vault.core.VaultSysOperations;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.core.VaultTransitOperations;
import org.springframework.vault.support.VaultMount;
import org.springframework.vault.support.VaultResponse;
import org.springframework.vault.support.VaultUnsealStatus;
import org.springframework.cloud.vault.config.VaultProperties;

@SpringBootApplication
public class VaultClientApplication implements CommandLineRunner {

	/*
	  For KV V1 at:   secret/cloud-vault-client
	  keys are:  fav  and  github.bogus.key
	 */
	@Value("${fav}")
	private String fav;
	@Value("${github.bogus.key}")
	private String githubBogusKey;



	@Autowired
	private VaultTemplate vaultTemplate;
/*
		Documentation:
		https://developer.hashicorp.com/vault/docs/concepts

        Example
        https://spring.io/guides/gs/accessing-vault

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

		/*
          Entering the following
              vault kv put -output-curl-string secret/github life=42
          Generate the following for info on the body to send and headers
              curl -X PUT -H "X-Vault-Request: true" -H "X-Vault-Token: $(vault print token)"
                   -d '{"data":{"life":"42"},"options":{}}' http://127.0.0.1:8200/v1/secret/data/github
         */


		// Determine if the status is current unsealed
		VaultUnsealStatus status = vaultTemplate.opsForSys().getUnsealStatus();

		// This version works as long as you provide a token
		VaultResponse response = vaultTemplate
			.opsForKeyValue("secret", KeyValueBackend.KV_1)  // Versioned api
			.get("cloud-vault-client");
			//.opsForKeyValue("kv", KeyValueBackend.KV_2)  // Versioned api
			//.get("cloud-vault-client");
			//.opsForKeyValue("secret", KeyValueBackend.KV_2)  // Versioned api
			//.get("github");


		//========================================================================
		// Lets Extract the key value
		//========================================================================
		System.out.println("Value of github.oauth2.key");
		System.out.println("-------------------------------");
		//System.out.println(response.getData().get("github.oauth2.key"));
		System.out.println(response.getData().get("github.bogus.key"));
		System.out.println("-------------------------------");
		System.out.println();


/*
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
