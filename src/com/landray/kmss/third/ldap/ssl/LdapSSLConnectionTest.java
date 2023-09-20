package com.landray.kmss.third.ldap.ssl;


import java.io.IOException;
import java.util.List;

import org.apache.directory.api.ldap.model.entry.DefaultModification;
import org.apache.directory.api.ldap.model.entry.Modification;
import org.apache.directory.api.ldap.model.entry.ModificationOperation;
import org.apache.directory.api.ldap.model.name.Dn;
import org.apache.directory.api.util.Network;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapConnectionConfig;
import org.apache.directory.ldap.client.api.LdapNetworkConnection;
import org.apache.directory.ldap.client.api.NoVerificationTrustManager;

/**
 * Test the LdapConnection class by enabling SSL and StartTLS one after the
 * other (using both in the same test class saves the time required to
 * start/stop another server for StartTLS)
 * 
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory
 *         Project</a>
 */

public class LdapSSLConnectionTest {
	private LdapConnectionConfig sslConfig;

	private LdapConnectionConfig tlsConfig;

	public void setup() throws Exception {
		sslConfig = new LdapConnectionConfig();
		sslConfig.setLdapHost("192.168.4.186");
		sslConfig.setUseSsl(true);
		sslConfig.setLdapPort(636);
		sslConfig.setTrustManagers(new NoVerificationTrustManager());
		// sslConfig.setBinaryAttributeDetector(
		// new DefaultConfigurableBinaryAttributeDetector());
		// SchemaBinaryAttributeDetector(new MyTrustManager(trustStore,
		// trustStorePassword.toCharArray()) );

		tlsConfig = new LdapConnectionConfig();
		tlsConfig.setLdapHost("WIN2012.2012.landray.local");
		tlsConfig.setLdapPort(389);
		tlsConfig.setTrustManagers(new NoVerificationTrustManager());
		// tlsConfig.setBinaryAttributeDetector( new
		// SchemaBinaryAttributeDetector(
		// ldapServer.getDirectoryService().getSchemaManager() ) );
	}

	public static void main(String[] args) {
		LdapSSLConnectionTest test = new LdapSSLConnectionTest();
		try {
			// System.setProperty("javax.net.ssl.trustStore",
			// "d:/certnew9.jks");

			// System.setProperty("javax.net.ssl.trustStorePassword", "123456");
			test.setup();
			test.testBindRequest();
			// test.testStartTLSBindRequest();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Test a successful bind request
	 *
	 * @throws IOException
	 */
	public void testBindRequest() throws Exception {
		LdapConnection connection = null;
		try {
			connection = new LdapNetworkConnection(sslConfig);
			connection.bind(
					"CN=Administrator,CN=Users,DC=2012,DC=landray,DC=local",
					"Landray123~");
			// ModificationItem[] mods = new ModificationItem[1];
			// String newQuotedPassword = "\"" + newPassword + "\"";
			// byte[] newUnicodePassword =
			// newQuotedPassword.getBytes("UTF-16LE");
			// mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
			// new BasicAttribute("unicodePwd", newUnicodePassword));

			byte[] newUnicodePassword = "\"Landray123456789~1\""
					.getBytes("UTF-16LE");
			Modification replaceGn = new DefaultModification(
					ModificationOperation.REPLACE_ATTRIBUTE, "unicodePwd",
					newUnicodePassword);

			Modification replaceGn2 = new DefaultModification(
					ModificationOperation.REPLACE_ATTRIBUTE, "sn",
					"test3");

			connection.modify(
					"CN=testtest,OU=landray,DC=2012,DC=landray,DC=local",
					replaceGn);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	public void testGetSupportedControls() throws Exception {
		LdapConnection connection = new LdapNetworkConnection(sslConfig);

		Dn dn = new Dn("uid=admin,ou=system");
		connection.bind(dn.getName(), "secret");

		List<String> controlList = connection.getSupportedControls();

		connection.close();
	}

	/**
	 * Test a successful bind request after setting up TLS
	 *
	 * @throws IOException
	 */
	public void testStartTLSBindRequest() throws Exception {
		LdapNetworkConnection connection = null;
		try {
			connection = new LdapNetworkConnection(tlsConfig);
			tlsConfig.setUseTls(true);
			connection.connect();

			connection.bind(
					"CN=Administrator,CN=Users,DC=2012,DC=landray,DC=local",
					"Landray123~");

			// try multiple binds with startTLS DIRAPI-173
			// connection.bind("uid=admin,ou=system", "secret");

			// connection.bind("uid=admin,ou=system", "secret");

			byte[] newUnicodePassword = "Landray12345~".getBytes("UTF-16LE");
			Modification replaceGn = new DefaultModification(
					ModificationOperation.REPLACE_ATTRIBUTE, "unicodePwd",
					newUnicodePassword);

			connection.modify(
					"CN=testtest,OU=landray,DC=2012,DC=landray,DC=local",
					replaceGn);

			connection.unBind();
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	public void testGetSupportedControlsWithStartTLS() throws Exception {
		LdapNetworkConnection connection = new LdapNetworkConnection(tlsConfig);
		tlsConfig.setUseTls(true);
		connection.connect();

		Dn dn = new Dn("uid=admin,ou=system");
		connection.bind(dn.getName(), "secret");

		List<String> controlList = connection.getSupportedControls();

		connection.close();
	}

	public void testFailsStartTLSWhenSSLIsInUse() throws Exception {
		LdapNetworkConnection connection = new LdapNetworkConnection(tlsConfig);
		tlsConfig.setUseSsl(true);
		tlsConfig.setLdapPort(636);
		connection.connect();
		connection.startTls();
		connection.close();
	}

	public void testStallingSsl() throws Exception {
		LdapConnectionConfig sslConfig = new LdapConnectionConfig();
		sslConfig.setLdapHost(Network.LOOPBACK_HOSTNAME);
		sslConfig.setUseSsl(true);
		sslConfig.setLdapPort(636);
		// sslConfig.setTrustManagers( new NoVerificationTrustManager() );

		LdapNetworkConnection connection = new LdapNetworkConnection(sslConfig);

		// We should get an exception here, as we don't have a trustManager
		// defined
		connection.bind();

		connection.close();
	}
}