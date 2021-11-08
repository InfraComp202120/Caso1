package Source;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;


/**
 * Source:	
 * https://mkyong.com/java/java-asymmetric-cryptography-example/
 */
public class KeyGen {

	private KeyPairGenerator keyGen;
	private KeyPair pair;
	private PrivateKey privateKey;
	private PublicKey publicKey;
	private File fPrivado;
	private File fPublico;

	public KeyGen(int keyLength, int numClientes) throws NoSuchAlgorithmException, NoSuchProviderException {

		fPublico = new File("./data/KeyPair/publicKey");
		fPrivado = new File("./data/KeyPair/privateKey");
		FileWriter fwPriv;
		FileWriter fwPub;
		try {
			fwPriv = new FileWriter(fPrivado);
			fwPub = new FileWriter(fPublico);

			for (int i = 0; i < numClientes+2; i++) {

				this.keyGen = KeyPairGenerator.getInstance("RSA");
				this.keyGen.initialize(keyLength);
				createKeys();
				Base64.Encoder encoder = Base64.getEncoder();

				writeToFile(fwPub, encoder.encodeToString(getPublicKey().getEncoded()), i, 0);
				writeToFile( fwPriv, encoder.encodeToString(getPrivateKey().getEncoded()), i, 1);
			}
			fwPriv.close();
			fwPub.close();
		}catch (IOException e) {
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

	public void writeToFile(FileWriter fw, String key, int i, int tipo) throws IOException {
		fw.write(key);
		fw.write("\n");
	}

	//	public static void main(String[] args) {
	//		KeyGen gk;
	//		try {
	//			gk = new KeyGen(1024);
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