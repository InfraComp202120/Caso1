package Source;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;


/**
 * Source:	
 * https://mkyong.com/java/java-asymmetric-cryptography-example/
 */
public class KeyGen {

	private KeyPairGenerator keyGen;
	private KeyPair pair;
	private PrivateKey privateKey;
	private PublicKey publicKey;

	public KeyGen(int keyLength) throws NoSuchAlgorithmException, NoSuchProviderException {
		this.keyGen = KeyPairGenerator.getInstance("RSA");
		this.keyGen.initialize(keyLength);
		createKeys();
		try {
			writeToFile("KeyPair/publicKey", getPublicKey().getEncoded());
			writeToFile("KeyPair/privateKey", getPrivateKey().getEncoded());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createKeys() {
		this.pair = this.keyGen.generateKeyPair();
		this.privateKey = pair.getPrivate();
		this.publicKey = pair.getPublic();
	}

	public PrivateKey getPrivateKey() {
		return this.privateKey;
	}

	public PublicKey getPublicKey() {
		return this.publicKey;
	}

	public void writeToFile(String path, byte[] key) throws IOException {
		File f = new File(path);
		f.getParentFile().mkdirs();

		FileOutputStream fos = new FileOutputStream(f);
		fos.write(key);
		fos.flush();
		fos.close();
	}

//	public static void main(String[] args) {
//		GenerateKeys gk;
//		try {
//			gk = new GenerateKeys(1024);
//			gk.createKeys();
//			gk.writeToFile("KeyPair/publicKey", gk.getPublicKey().getEncoded());
//			gk.writeToFile("KeyPair/privateKey", gk.getPrivateKey().getEncoded());
//		} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
//			System.err.println(e.getMessage());
//		} catch (IOException e) {
//			System.err.println(e.getMessage());
//		}
//
//	}

}