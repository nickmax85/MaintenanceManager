package url;

import java.awt.Desktop;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;

public class Web {

	public static void main(String[] args) {
		new Web();

	}

	public Web() {

		try {
			String simpleUrl = "http://www.google.at";

			simpleUrl = "http://apps-eu.magnapowertrain.com/01/plantmaintenance/SAB%20Standard%20Arbeitsblatt%20TPM/OM1%20Montage/ATCs/ATC350/SAB_Wartungsplan_ATC%20350_141203_Version%201.4_MPT.pdf";
			// simpleUrl =
			// "http://apps-eu.magnapowertrain.com/01/plantmaintenance/SAB%20Standard%20Arbeitsblatt%20TPM/Forms/AllItems.aspx?RootFolder=%2F01%2Fplantmaintenance%2FSAB%20Standard%20Arbeitsblatt%20TPM%2FOM1%20Montage%2FATCs%2FATC350&FolderCTID=0x012000D4B81EB8F3BA2648BA3CE24C96896040&View={9DFC1AAD-C6C3-464E-B064-68CAD7F1BB6B}";

			// System.out.println(simpleUrl.charAt(274));
			String encodedurl = URLEncoder.encode(simpleUrl, "UTF-8");

			URI myUri = URI.create(simpleUrl);
			Desktop.getDesktop().browse(myUri);

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
