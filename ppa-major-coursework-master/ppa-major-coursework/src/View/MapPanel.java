package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Controller.MapIconsML;
import Model.IncidentFetcher;
import api.ripley.Incident;

/**
 * This class implements Observer, this class displays the map. 
 * @author Dan, Stefan
 *
 */
public class MapPanel extends JPanel implements Observer {

	private static final long serialVersionUID = -8254503890223363181L;
	// Image field for background
	private ImageIcon map;
	// Image fields to hold each type of alien icon
	private ImageIcon alienIconSmall;
	private ImageIcon alienIconMed;
	private ImageIcon alienIconLarge;
	// Incident fetcher to handle logic and computations
	private IncidentFetcher incidentFetcher;
	// Field to allow multiple states' incidents to be shown at the same time
	private ArrayList<StateListPopupFrame> popups;

	// Labels to hold each state alien icon
	private JLabel alLabelIcon;
	private JLabel akLabelIcon;
	private JLabel azLabelIcon;
	private JLabel arLabelIcon;
	private JLabel caLabelIcon;
	private JLabel coLabelIcon;
	private JLabel ctLabelIcon;
	private JLabel deLabelIcon;
	private JLabel flLabelIcon;
	private JLabel gaLabelIcon;
	private JLabel hiLabelIcon;
	private JLabel idLabelIcon;
	private JLabel ilLabelIcon;
	private JLabel inLabelIcon;
	private JLabel iaLabelIcon;
	private JLabel ksLabelIcon;
	private JLabel kyLabelIcon;
	private JLabel laLabelIcon;
	private JLabel meLabelIcon;
	private JLabel mdLabelIcon;
	private JLabel maLabelIcon;
	private JLabel miLabelIcon;
	private JLabel mnLabelIcon;
	private JLabel msLabelIcon;
	private JLabel moLabelIcon;
	private JLabel mtLabelIcon;
	private JLabel neLabelIcon;
	private JLabel nvLabelIcon;
	private JLabel nhLabelIcon;
	private JLabel njLabelIcon;
	private JLabel nmLabelIcon;
	private JLabel nyLabelIcon;
	private JLabel ncLabelIcon;
	private JLabel ndLabelIcon;
	private JLabel ohLabelIcon;
	private JLabel okLabelIcon;
	private JLabel orLabelIcon;
	private JLabel paLabelIcon;
	private JLabel riLabelIcon;
	private JLabel scLabelIcon;
	private JLabel sdLabelIcon;
	private JLabel tnLabelIcon;
	private JLabel txLabelIcon;
	private JLabel utLabelIcon;
	private JLabel vtLabelIcon;
	private JLabel vaLabelIcon;
	private JLabel waLabelIcon;
	private JLabel wvLabelIcon;
	private JLabel wiLabelIcon;
	private JLabel wyLabelIcon;
	// Array to hold all labels assigned a state name
	private ArrayList<JLabel> statesIconArray;
	// Mouse Listener for each icon
	private MapIconsML stateML;

	// Labels to hold each US state image
	private JLabel alLabelState;
	private JLabel akLabelState;
	private JLabel azLabelState;
	private JLabel arLabelState;
	private JLabel caLabelState;
	private JLabel coLabelState;
	private JLabel ctLabelState;
	private JLabel deLabelState;
	private JLabel flLabelState;
	private JLabel gaLabelState;
	private JLabel hiLabelState;
	private JLabel idLabelState;
	private JLabel ilLabelState;
	private JLabel inLabelState;
	private JLabel iaLabelState;
	private JLabel ksLabelState;
	private JLabel kyLabelState;
	private JLabel laLabelState;
	private JLabel meLabelState;
	private JLabel mdLabelState;
	private JLabel maLabelState;
	private JLabel miLabelState;
	private JLabel mnLabelState;
	private JLabel msLabelState;
	private JLabel moLabelState;
	private JLabel mtLabelState;
	private JLabel neLabelState;
	private JLabel nvLabelState;
	private JLabel nhLabelState;
	private JLabel njLabelState;
	private JLabel nmLabelState;
	private JLabel nyLabelState;
	private JLabel ncLabelState;
	private JLabel ndLabelState;
	private JLabel ohLabelState;
	private JLabel okLabelState;
	private JLabel orLabelState;
	private JLabel paLabelState;
	private JLabel riLabelState;
	private JLabel scLabelState;
	private JLabel sdLabelState;
	private JLabel tnLabelState;
	private JLabel txLabelState;
	private JLabel utLabelState;
	private JLabel vtLabelState;
	private JLabel vaLabelState;
	private JLabel waLabelState;
	private JLabel wvLabelState;
	private JLabel wiLabelState;
	private JLabel wyLabelState;
	// Array to hold all labels assigned a state name
	private ArrayList<JLabel> statesImgArray;

	// Buffered images to hold the images of states
	private BufferedImage alBI;
	private BufferedImage akBI;
	private BufferedImage azBI;
	private BufferedImage arBI;
	private BufferedImage caBI;
	private BufferedImage coBI;
	private BufferedImage ctBI;
	private BufferedImage deBI;
	private BufferedImage flBI;
	private BufferedImage gaBI;
	private BufferedImage hiBI;
	private BufferedImage idBI;
	private BufferedImage ilBI;
	private BufferedImage inBI;
	private BufferedImage iaBI;
	private BufferedImage ksBI;
	private BufferedImage kyBI;
	private BufferedImage laBI;
	private BufferedImage meBI;
	private BufferedImage mdBI;
	private BufferedImage maBI;
	private BufferedImage miBI;
	private BufferedImage mnBI;
	private BufferedImage msBI;
	private BufferedImage moBI;
	private BufferedImage mtBI;
	private BufferedImage neBI;
	private BufferedImage nvBI;
	private BufferedImage nhBI;
	private BufferedImage njBI;
	private BufferedImage nmBI;
	private BufferedImage nyBI;
	private BufferedImage ncBI;
	private BufferedImage ndBI;
	private BufferedImage ohBI;
	private BufferedImage okBI;
	private BufferedImage orBI;
	private BufferedImage paBI;
	private BufferedImage riBI;
	private BufferedImage scBI;
	private BufferedImage sdBI;
	private BufferedImage tnBI;
	private BufferedImage txBI;
	private BufferedImage utBI;
	private BufferedImage vtBI;
	private BufferedImage vaBI;
	private BufferedImage waBI;
	private BufferedImage wvBI;
	private BufferedImage wiBI;
	private BufferedImage wyBI;

	public MapPanel(IncidentFetcher incidentFetcher) {
		super();

		this.incidentFetcher = incidentFetcher;
		stateML = new MapIconsML(incidentFetcher);
		popups = new ArrayList<StateListPopupFrame>();
		statesIconArray = new ArrayList<JLabel>();
		statesImgArray = new ArrayList<JLabel>();
		alienIconSmall = new ImageIcon("resources/fleshpod_marker.png");
		alienIconMed = new ImageIcon("resources/facehugger_marker.png");
		alienIconLarge = new ImageIcon("resources/alien_marker.png");
		IniWig();
	}

	/**
	 * Method to initialise widgets 
	 */
	public void IniWig() {
		
		stateML.addObserver(this);
		incidentFetcher.addObserver(this);
		this.setPreferredSize(new Dimension(1054, 567));
		this.setLayout(null);

		// initialise Icon labels
		alLabelIcon = new JLabel();
		akLabelIcon = new JLabel();
		azLabelIcon = new JLabel();
		arLabelIcon = new JLabel();
		caLabelIcon = new JLabel();
		coLabelIcon = new JLabel();
		ctLabelIcon = new JLabel();
		deLabelIcon = new JLabel();
		flLabelIcon = new JLabel();
		gaLabelIcon = new JLabel();
		hiLabelIcon = new JLabel();
		idLabelIcon = new JLabel();
		ilLabelIcon = new JLabel();
		inLabelIcon = new JLabel();
		iaLabelIcon = new JLabel();
		ksLabelIcon = new JLabel();
		kyLabelIcon = new JLabel();
		laLabelIcon = new JLabel();
		meLabelIcon = new JLabel();
		mdLabelIcon = new JLabel();
		maLabelIcon = new JLabel();
		miLabelIcon = new JLabel();
		mnLabelIcon = new JLabel();
		msLabelIcon = new JLabel();
		moLabelIcon = new JLabel();
		mtLabelIcon = new JLabel();
		neLabelIcon = new JLabel();
		nvLabelIcon = new JLabel();
		nhLabelIcon = new JLabel();
		njLabelIcon = new JLabel();
		nmLabelIcon = new JLabel();
		nyLabelIcon = new JLabel();
		ncLabelIcon = new JLabel();
		ndLabelIcon = new JLabel();
		ohLabelIcon = new JLabel();
		okLabelIcon = new JLabel();
		orLabelIcon = new JLabel();
		paLabelIcon = new JLabel();
		riLabelIcon = new JLabel();
		scLabelIcon = new JLabel();
		sdLabelIcon = new JLabel();
		tnLabelIcon = new JLabel();
		txLabelIcon = new JLabel();
		utLabelIcon = new JLabel();
		vtLabelIcon = new JLabel();
		vaLabelIcon = new JLabel();
		waLabelIcon = new JLabel();
		wvLabelIcon = new JLabel();
		wiLabelIcon = new JLabel();
		wyLabelIcon = new JLabel();

		// initialise labels for state images
		alLabelState = new JLabel();
		akLabelState = new JLabel();
		azLabelState = new JLabel();
		arLabelState = new JLabel();
		caLabelState = new JLabel();
		coLabelState = new JLabel();
		ctLabelState = new JLabel();
		deLabelState = new JLabel();
		flLabelState = new JLabel();
		gaLabelState = new JLabel();
		hiLabelState = new JLabel();
		idLabelState = new JLabel();
		ilLabelState = new JLabel();
		inLabelState = new JLabel();
		iaLabelState = new JLabel();
		ksLabelState = new JLabel();
		kyLabelState = new JLabel();
		laLabelState = new JLabel();
		meLabelState = new JLabel();
		mdLabelState = new JLabel();
		maLabelState = new JLabel();
		miLabelState = new JLabel();
		mnLabelState = new JLabel();
		msLabelState = new JLabel();
		moLabelState = new JLabel();
		mtLabelState = new JLabel();
		neLabelState = new JLabel();
		nvLabelState = new JLabel();
		nhLabelState = new JLabel();
		njLabelState = new JLabel();
		nmLabelState = new JLabel();
		nyLabelState = new JLabel();
		ncLabelState = new JLabel();
		ndLabelState = new JLabel();
		ohLabelState = new JLabel();
		okLabelState = new JLabel();
		orLabelState = new JLabel();
		paLabelState = new JLabel();
		riLabelState = new JLabel();
		scLabelState = new JLabel();
		sdLabelState = new JLabel();
		tnLabelState = new JLabel();
		txLabelState = new JLabel();
		utLabelState = new JLabel();
		vtLabelState = new JLabel();
		vaLabelState = new JLabel();
		waLabelState = new JLabel();
		wvLabelState = new JLabel();
		wiLabelState = new JLabel();
		wyLabelState = new JLabel();

		populateStatesIconArray();
		populatestatesImgArray();
		assignStateNames(statesIconArray);
		assignStateNames(statesImgArray);

		//Add state Icon labels, set coordinates and size using bounds. Default size of 0, 0 
		this.add(statesIconArray.get(0));
		statesIconArray.get(0).setBounds(673, 359, 0, 0);
		this.add(statesIconArray.get(1));
		statesIconArray.get(1).setBounds(145, 461, 0, 0);
		this.add(statesIconArray.get(2));
		statesIconArray.get(2).setBounds(250, 338, 0, 0);
		this.add(statesIconArray.get(3));
		statesIconArray.get(3).setBounds(577, 334, 0, 0);
		this.add(statesIconArray.get(4));
		statesIconArray.get(4).setBounds(126, 244, 0, 0);
		this.add(statesIconArray.get(5));
		statesIconArray.get(5).setBounds(359, 250, 0, 0);
		this.add(statesIconArray.get(6));
		statesIconArray.get(6).setBounds(1003, 172, 0, 0);
		this.add(statesIconArray.get(7));
		statesIconArray.get(7).setBounds(1003, 230, 0, 0);
		this.add(statesIconArray.get(8));
		statesIconArray.get(8).setBounds(774, 424, 0, 0);
		this.add(statesIconArray.get(9));
		statesIconArray.get(9).setBounds(734, 359, 0, 0);
		this.add(statesIconArray.get(10));
		statesIconArray.get(10).setBounds(312, 526, 0, 0);
		this.add(statesIconArray.get(11));
		statesIconArray.get(11).setBounds(242, 134, 0, 0);
		this.add(statesIconArray.get(12));
		statesIconArray.get(12).setBounds(617, 216, 0, 0);
		this.add(statesIconArray.get(13));
		statesIconArray.get(13).setBounds(661, 214, 0, 0);
		this.add(statesIconArray.get(14));
		statesIconArray.get(14).setBounds(544, 187, 0, 0);
		this.add(statesIconArray.get(15));
		statesIconArray.get(15).setBounds(471, 262, 0, 0);
		this.add(statesIconArray.get(16));
		statesIconArray.get(16).setBounds(693, 261, 0, 0);
		this.add(statesIconArray.get(17));
		statesIconArray.get(17).setBounds(580, 408, 0, 0);
		this.add(statesIconArray.get(18));
		statesIconArray.get(18).setBounds(873, 37, 0, 0);
		this.add(statesIconArray.get(19));
		statesIconArray.get(19).setBounds(1003, 260, 0, 0);
		this.add(statesIconArray.get(20));
		statesIconArray.get(20).setBounds(1003, 117, 0, 0);
		this.add(statesIconArray.get(21));
		statesIconArray.get(21).setBounds(664, 140, 0, 0);
		this.add(statesIconArray.get(22));
		statesIconArray.get(22).setBounds(525, 107, 0, 0);
		this.add(statesIconArray.get(23));
		statesIconArray.get(23).setBounds(626, 382, 0, 0);
		this.add(statesIconArray.get(24));
		statesIconArray.get(24).setBounds(568, 258, 0, 0);
		this.add(statesIconArray.get(25));
		statesIconArray.get(25).setBounds(321, 74, 0, 0);
		this.add(statesIconArray.get(26));
		statesIconArray.get(26).setBounds(457, 204, 0, 0);
		this.add(statesIconArray.get(27));
		statesIconArray.get(27).setBounds(195, 220, 0, 0);
		this.add(statesIconArray.get(28));
		statesIconArray.get(28).setBounds(1003, 90, 0, 0);
		this.add(statesIconArray.get(29));
		statesIconArray.get(29).setBounds(1003, 200, 0, 0);
		this.add(statesIconArray.get(30));
		statesIconArray.get(30).setBounds(354, 343, 0, 0);
		this.add(statesIconArray.get(31));
		statesIconArray.get(31).setBounds(807, 115, 0, 0);
		this.add(statesIconArray.get(32));
		statesIconArray.get(32).setBounds(794, 281, 0, 0);
		this.add(statesIconArray.get(33));
		statesIconArray.get(33).setBounds(439, 80, 0, 0);
		this.add(statesIconArray.get(34));
		statesIconArray.get(34).setBounds(709, 197, 0, 0);
		this.add(statesIconArray.get(35));
		statesIconArray.get(35).setBounds(492, 332, 0, 0);
		this.add(statesIconArray.get(36));
		statesIconArray.get(36).setBounds(145, 120, 0, 0);
		this.add(statesIconArray.get(37));
		statesIconArray.get(37).setBounds(783, 170, 0, 0);
		this.add(statesIconArray.get(38));
		statesIconArray.get(38).setBounds(1003, 145, 0, 0);
		this.add(statesIconArray.get(39));
		statesIconArray.get(39).setBounds(771, 321, 0, 0);
		this.add(statesIconArray.get(40));
		statesIconArray.get(40).setBounds(445, 139, 0, 0);
		this.add(statesIconArray.get(41));
		statesIconArray.get(41).setBounds(663, 302, 0, 0);
		this.add(statesIconArray.get(42));
		statesIconArray.get(42).setBounds(465, 413, 0, 0);
		this.add(statesIconArray.get(43));
		statesIconArray.get(43).setBounds(265, 241, 0, 0);
		this.add(statesIconArray.get(44));
		statesIconArray.get(44).setBounds(1003, 66, 0, 0);
		this.add(statesIconArray.get(45));
		statesIconArray.get(45).setBounds(792, 236, 0, 0);
		this.add(statesIconArray.get(46));
		statesIconArray.get(46).setBounds(176, 52, 0, 0);
		this.add(statesIconArray.get(47));
		statesIconArray.get(47).setBounds(751, 233, 0, 0);
		this.add(statesIconArray.get(48));
		statesIconArray.get(48).setBounds(600, 130, 0, 0);
		this.add(statesIconArray.get(49));
		statesIconArray.get(49).setBounds(337, 169, 0, 0);

		
		//Load each state image into a buffered image using ImageI/O reading from a file directory
		//Try and catch incase any files are missing
		try {
			alBI = ImageIO.read(new File("resources/states/AL.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newal = new ImageIcon(alBI);

		try {
			akBI = ImageIO.read(new File("resources/states/AK.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newAK = new ImageIcon(akBI);

		try {
			azBI = ImageIO.read(new File("resources/states/AZ.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newaz = new ImageIcon(azBI);

		try {
			arBI = ImageIO.read(new File("resources/states/AR.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newar = new ImageIcon(arBI);

		try {
			caBI = ImageIO.read(new File("resources/states/CA.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newca = new ImageIcon(caBI);

		try {
			coBI = ImageIO.read(new File("resources/states/CO.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newco = new ImageIcon(coBI);

		try {
			ctBI = ImageIO.read(new File("resources/states/CT.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newct = new ImageIcon(ctBI);

		try {
			deBI = ImageIO.read(new File("resources/states/DE.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newde = new ImageIcon(deBI);

		try {
			flBI = ImageIO.read(new File("resources/states/FL.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newfl = new ImageIcon(flBI);

		try {
			gaBI = ImageIO.read(new File("resources/states/GA.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newga = new ImageIcon(gaBI);

		try {
			hiBI = ImageIO.read(new File("resources/states/HI.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newhi = new ImageIcon(hiBI);

		try {
			idBI = ImageIO.read(new File("resources/states/ID.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newid = new ImageIcon(idBI);

		try {
			ilBI = ImageIO.read(new File("resources/states/IL.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newil = new ImageIcon(ilBI);

		try {
			inBI = ImageIO.read(new File("resources/states/IN.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newin = new ImageIcon(inBI);

		try {
			iaBI = ImageIO.read(new File("resources/states/IA.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newia = new ImageIcon(iaBI);

		try {
			ksBI = ImageIO.read(new File("resources/states/KS.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newks = new ImageIcon(ksBI);

		try {
			kyBI = ImageIO.read(new File("resources/states/KY.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newky = new ImageIcon(kyBI);

		try {
			laBI = ImageIO.read(new File("resources/states/LA.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newla = new ImageIcon(laBI);

		try {
			meBI = ImageIO.read(new File("resources/states/ME.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newme = new ImageIcon(meBI);

		try {
			mdBI = ImageIO.read(new File("resources/states/MD.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newmd = new ImageIcon(mdBI);

		try {
			maBI = ImageIO.read(new File("resources/states/MA.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newma = new ImageIcon(maBI);

		try {
			miBI = ImageIO.read(new File("resources/states/MI.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newmi = new ImageIcon(miBI);

		try {
			mnBI = ImageIO.read(new File("resources/states/MN.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newmn = new ImageIcon(mnBI);

		try {
			msBI = ImageIO.read(new File("resources/states/MS.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newms = new ImageIcon(msBI);

		try {
			moBI = ImageIO.read(new File("resources/states/MO.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newmo = new ImageIcon(moBI);

		try {
			mtBI = ImageIO.read(new File("resources/states/MT.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newmt = new ImageIcon(mtBI);

		try {
			neBI = ImageIO.read(new File("resources/states/NE.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newne = new ImageIcon(neBI);

		try {
			nvBI = ImageIO.read(new File("resources/states/NV.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newnv = new ImageIcon(nvBI);

		try {
			nhBI = ImageIO.read(new File("resources/states/NH.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newnh = new ImageIcon(nhBI);

		try {
			njBI = ImageIO.read(new File("resources/states/NJ.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newnj = new ImageIcon(njBI);

		try {
			nmBI = ImageIO.read(new File("resources/states/NM.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newnm = new ImageIcon(nmBI);

		try {
			nyBI = ImageIO.read(new File("resources/states/NY.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newny = new ImageIcon(nyBI);

		try {
			ncBI = ImageIO.read(new File("resources/states/NC.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newnc = new ImageIcon(ncBI);

		try {
			ndBI = ImageIO.read(new File("resources/states/ND.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newnd = new ImageIcon(ndBI);

		try {
			ohBI = ImageIO.read(new File("resources/states/OH.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newoh = new ImageIcon(ohBI);

		try {
			okBI = ImageIO.read(new File("resources/states/OK.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newok = new ImageIcon(okBI);

		try {
			orBI = ImageIO.read(new File("resources/states/OR.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newor = new ImageIcon(orBI);

		try {
			paBI = ImageIO.read(new File("resources/states/PA.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newpa = new ImageIcon(paBI);

		try {
			riBI = ImageIO.read(new File("resources/states/RI.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newri = new ImageIcon(riBI);

		try {
			scBI = ImageIO.read(new File("resources/states/SC.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newsc = new ImageIcon(scBI);

		try {
			sdBI = ImageIO.read(new File("resources/states/SD.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newsd = new ImageIcon(sdBI);

		try {
			tnBI = ImageIO.read(new File("resources/states/TN.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newtn = new ImageIcon(tnBI);

		try {
			txBI = ImageIO.read(new File("resources/states/TX.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newtx = new ImageIcon(txBI);

		try {
			utBI = ImageIO.read(new File("resources/states/UT.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newut = new ImageIcon(utBI);

		try {
			vtBI = ImageIO.read(new File("resources/states/VT.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newvt = new ImageIcon(vtBI);

		try {
			vaBI = ImageIO.read(new File("resources/states/VA.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newva = new ImageIcon(vaBI);

		try {
			waBI = ImageIO.read(new File("resources/states/WA.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newwa = new ImageIcon(waBI);

		try {
			wvBI = ImageIO.read(new File("resources/states/WV.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newwv = new ImageIcon(wvBI);

		try {
			wiBI = ImageIO.read(new File("resources/states/WI.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newwi = new ImageIcon(wiBI);

		try {
			wyBI = ImageIO.read(new File("resources/states/WY.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon newwy = new ImageIcon(wyBI);

		// Add labels to panel and set location + size for when images are
		this.add(statesImgArray.get(0));
		statesImgArray.get(0).setBounds(660, 329, 66, 104);
		statesImgArray.get(0).setIcon(newal);
		this.add(statesImgArray.get(1));
		statesImgArray.get(1).setBounds(116, 436, 100, 100);
		statesImgArray.get(1).setIcon(newAK);
		this.add(statesImgArray.get(2));
		statesImgArray.get(2).setBounds(206, 290, 112, 130);
		statesImgArray.get(2).setIcon(newaz);
		this.add(statesImgArray.get(3));
		statesImgArray.get(3).setBounds(551, 311, 83, 78);
		statesImgArray.get(3).setIcon(newar);
		this.add(statesImgArray.get(4));
		statesImgArray.get(4).setBounds(86, 142, 150, 250);
		statesImgArray.get(4).setIcon(newca);
		this.add(statesImgArray.get(5));
		statesImgArray.get(5).setBounds(315, 220, 120, 92);
		statesImgArray.get(5).setIcon(newco);
		this.add(statesImgArray.get(6));
		statesImgArray.get(6).setBounds(854, 134, 29, 31);
		statesImgArray.get(6).setIcon(newct);
		this.add(statesImgArray.get(7));
		statesImgArray.get(7).setBounds(836, 197, 20, 30);
		statesImgArray.get(7).setIcon(newde);
		this.add(statesImgArray.get(8));
		statesImgArray.get(8).setBounds(677, 400, 154, 114);
		statesImgArray.get(8).setIcon(newfl);
		this.add(statesImgArray.get(9));
		statesImgArray.get(9).setBounds(700, 322, 92, 95);
		statesImgArray.get(9).setIcon(newga);
		this.add(statesImgArray.get(10));
		statesImgArray.get(10).setBounds(310, 504, 63, 27);
		statesImgArray.get(10).setIcon(newhi);
		this.add(statesImgArray.get(11));
		statesImgArray.get(11).setBounds(206, 42, 98, 160);
		statesImgArray.get(11).setIcon(newid);
		this.add(statesImgArray.get(12));
		statesImgArray.get(12).setBounds(595, 184, 68, 118);
		statesImgArray.get(12).setIcon(newil);
		this.add(statesImgArray.get(13));
		statesImgArray.get(13).setBounds(650, 190, 53, 89);
		statesImgArray.get(13).setIcon(newin);
		this.add(statesImgArray.get(14));
		statesImgArray.get(14).setBounds(514, 170, 103, 67);
		statesImgArray.get(14).setIcon(newia);
		this.add(statesImgArray.get(15));
		statesImgArray.get(15).setBounds(427, 245, 125, 66);
		statesImgArray.get(15).setIcon(newks);
		this.add(statesImgArray.get(16));
		statesImgArray.get(16).setBounds(635, 243, 117, 67);
		statesImgArray.get(16).setIcon(newky);
		this.add(statesImgArray.get(17));
		statesImgArray.get(17).setBounds(566, 381, 98, 81);
		statesImgArray.get(17).setIcon(newla);
		this.add(statesImgArray.get(18));
		statesImgArray.get(18).setBounds(853, 9, 112, 100);
		statesImgArray.get(18).setIcon(newme);
		this.add(statesImgArray.get(19));
		statesImgArray.get(19).setBounds(779, 199, 76, 37);
		statesImgArray.get(19).setIcon(newmd);
		this.add(statesImgArray.get(20));
		statesImgArray.get(20).setBounds(853, 113, 50, 31);
		statesImgArray.get(20).setIcon(newma);
		this.add(statesImgArray.get(21));
		statesImgArray.get(21).setBounds(595, 88, 132, 110);
		statesImgArray.get(21).setIcon(newmi);
		this.add(statesImgArray.get(22));
		statesImgArray.get(22).setBounds(500, 54, 104, 126);
		statesImgArray.get(22).setIcon(newmn);
		this.add(statesImgArray.get(23));
		statesImgArray.get(23).setBounds(609, 335, 61, 103);
		statesImgArray.get(23).setIcon(newms);
		this.add(statesImgArray.get(24));
		statesImgArray.get(24).setBounds(526, 229, 117, 95);
		statesImgArray.get(24).setIcon(newmo);
		this.add(statesImgArray.get(25));
		statesImgArray.get(25).setBounds(243, 37, 200, 120);
		statesImgArray.get(25).setIcon(newmt);
		this.add(statesImgArray.get(26));
		statesImgArray.get(26).setBounds(401, 186, 140, 65);
		statesImgArray.get(26).setIcon(newne);
		this.add(statesImgArray.get(27));
		statesImgArray.get(27).setBounds(147, 167, 150, 170);
		statesImgArray.get(27).setIcon(newnv);
		this.add(statesImgArray.get(28));
		statesImgArray.get(28).setBounds(860, 67, 28, 60);
		statesImgArray.get(28).setIcon(newnh);
		this.add(statesImgArray.get(29));
		statesImgArray.get(29).setBounds(838, 160, 22, 48);
		statesImgArray.get(29).setIcon(newnj);
		this.add(statesImgArray.get(30));
		statesImgArray.get(30).setBounds(305, 298, 113, 121);
		statesImgArray.get(30).setIcon(newnm);
		this.add(statesImgArray.get(31));
		statesImgArray.get(31).setBounds(761, 83, 100, 88);
		statesImgArray.get(31).setIcon(newny);
		this.add(statesImgArray.get(32));
		statesImgArray.get(32).setBounds(720, 262, 137, 67);
		statesImgArray.get(32).setIcon(newnc);
		this.add(statesImgArray.get(33));
		statesImgArray.get(33).setBounds(404, 65, 111, 66);
		statesImgArray.get(33).setIcon(newnd);
		this.add(statesImgArray.get(34));
		statesImgArray.get(34).setBounds(690, 172, 72, 81);
		statesImgArray.get(34).setIcon(newoh);
		this.add(statesImgArray.get(35));
		statesImgArray.get(35).setBounds(410, 306, 149, 71);
		statesImgArray.get(35).setIcon(newok);
		this.add(statesImgArray.get(36));
		statesImgArray.get(36).setBounds(100, 55, 150, 150);
		statesImgArray.get(36).setIcon(newor);
		this.add(statesImgArray.get(37));
		statesImgArray.get(37).setBounds(753, 151, 94, 69);
		statesImgArray.get(37).setIcon(newpa);
		this.add(statesImgArray.get(38));
		statesImgArray.get(38).setBounds(877, 133, 10, 15);
		statesImgArray.get(38).setIcon(newri);
		this.add(statesImgArray.get(39));
		statesImgArray.get(39).setBounds(739, 311, 84, 63);
		statesImgArray.get(39).setIcon(newsc);
		this.add(statesImgArray.get(40));
		statesImgArray.get(40).setBounds(403, 126, 118, 72);
		statesImgArray.get(40).setIcon(newsd);
		this.add(statesImgArray.get(41));
		statesImgArray.get(41).setBounds(624, 284, 139, 59);
		statesImgArray.get(41).setIcon(newtn);
		this.add(statesImgArray.get(42));
		statesImgArray.get(42).setBounds(344, 314, 236, 222);
		statesImgArray.get(42).setIcon(newtx);
		this.add(statesImgArray.get(43));
		statesImgArray.get(43).setBounds(234, 188, 93, 116);
		statesImgArray.get(43).setIcon(newut);
		this.add(statesImgArray.get(44));
		statesImgArray.get(44).setBounds(837, 74, 28, 56);
		statesImgArray.get(44).setIcon(newvt);
		this.add(statesImgArray.get(45));
		statesImgArray.get(45).setBounds(731, 214, 120, 79);
		statesImgArray.get(45).setIcon(newva);
		this.add(statesImgArray.get(46));
		statesImgArray.get(46).setBounds(129, 14, 115, 100);
		statesImgArray.get(46).setIcon(newwa);
		this.add(statesImgArray.get(47));
		statesImgArray.get(47).setBounds(736, 199, 74, 76);
		statesImgArray.get(47).setIcon(newwv);
		this.add(statesImgArray.get(48));
		statesImgArray.get(48).setBounds(563, 101, 84, 92);
		statesImgArray.get(48).setIcon(newwi);
		this.add(statesImgArray.get(49));
		statesImgArray.get(49).setBounds(293, 136, 115, 94);
		statesImgArray.get(49).setIcon(newwy);

		for (int i = 0; i < statesIconArray.size(); i++) {
			statesIconArray.get(i).addMouseListener(stateML);
		}

	}
	/**
	 * Add objects to the statesImgArray
	 */
	private void populatestatesImgArray() {
		statesImgArray.add(alLabelState);
		statesImgArray.add(akLabelState);
		statesImgArray.add(azLabelState);
		statesImgArray.add(arLabelState);
		statesImgArray.add(caLabelState);
		statesImgArray.add(coLabelState);
		statesImgArray.add(ctLabelState);
		statesImgArray.add(deLabelState);
		statesImgArray.add(flLabelState);
		statesImgArray.add(gaLabelState);
		statesImgArray.add(hiLabelState);
		statesImgArray.add(idLabelState);
		statesImgArray.add(ilLabelState);
		statesImgArray.add(inLabelState);
		statesImgArray.add(iaLabelState);
		statesImgArray.add(ksLabelState);
		statesImgArray.add(kyLabelState);
		statesImgArray.add(laLabelState);
		statesImgArray.add(meLabelState);
		statesImgArray.add(mdLabelState);
		statesImgArray.add(maLabelState);
		statesImgArray.add(miLabelState);
		statesImgArray.add(mnLabelState);
		statesImgArray.add(msLabelState);
		statesImgArray.add(moLabelState);
		statesImgArray.add(mtLabelState);
		statesImgArray.add(neLabelState);
		statesImgArray.add(nvLabelState);
		statesImgArray.add(nhLabelState);
		statesImgArray.add(njLabelState);
		statesImgArray.add(nmLabelState);
		statesImgArray.add(nyLabelState);
		statesImgArray.add(ncLabelState);
		statesImgArray.add(ndLabelState);
		statesImgArray.add(ohLabelState);
		statesImgArray.add(okLabelState);
		statesImgArray.add(orLabelState);
		statesImgArray.add(paLabelState);
		statesImgArray.add(riLabelState);
		statesImgArray.add(scLabelState);
		statesImgArray.add(sdLabelState);
		statesImgArray.add(tnLabelState);
		statesImgArray.add(txLabelState);
		statesImgArray.add(utLabelState);
		statesImgArray.add(vtLabelState);
		statesImgArray.add(vaLabelState);
		statesImgArray.add(waLabelState);
		statesImgArray.add(wvLabelState);
		statesImgArray.add(wiLabelState);
		statesImgArray.add(wyLabelState);
	}

	/**
	 * Add objects to the statesIconArray
	 */
	private void populateStatesIconArray() {
		statesIconArray.add(alLabelIcon);
		statesIconArray.add(akLabelIcon);
		statesIconArray.add(azLabelIcon);
		statesIconArray.add(arLabelIcon);
		statesIconArray.add(caLabelIcon);
		statesIconArray.add(coLabelIcon);
		statesIconArray.add(ctLabelIcon);
		statesIconArray.add(deLabelIcon);
		statesIconArray.add(flLabelIcon);
		statesIconArray.add(gaLabelIcon);
		statesIconArray.add(hiLabelIcon);
		statesIconArray.add(idLabelIcon);
		statesIconArray.add(ilLabelIcon);
		statesIconArray.add(inLabelIcon);
		statesIconArray.add(iaLabelIcon);
		statesIconArray.add(ksLabelIcon);
		statesIconArray.add(kyLabelIcon);
		statesIconArray.add(laLabelIcon);
		statesIconArray.add(meLabelIcon);
		statesIconArray.add(mdLabelIcon);
		statesIconArray.add(maLabelIcon);
		statesIconArray.add(miLabelIcon);
		statesIconArray.add(mnLabelIcon);
		statesIconArray.add(msLabelIcon);
		statesIconArray.add(moLabelIcon);
		statesIconArray.add(mtLabelIcon);
		statesIconArray.add(neLabelIcon);
		statesIconArray.add(nvLabelIcon);
		statesIconArray.add(nhLabelIcon);
		statesIconArray.add(njLabelIcon);
		statesIconArray.add(nmLabelIcon);
		statesIconArray.add(nyLabelIcon);
		statesIconArray.add(ncLabelIcon);
		statesIconArray.add(ndLabelIcon);
		statesIconArray.add(ohLabelIcon);
		statesIconArray.add(okLabelIcon);
		statesIconArray.add(orLabelIcon);
		statesIconArray.add(paLabelIcon);
		statesIconArray.add(riLabelIcon);
		statesIconArray.add(scLabelIcon);
		statesIconArray.add(sdLabelIcon);
		statesIconArray.add(tnLabelIcon);
		statesIconArray.add(txLabelIcon);
		statesIconArray.add(utLabelIcon);
		statesIconArray.add(vtLabelIcon);
		statesIconArray.add(vaLabelIcon);
		statesIconArray.add(waLabelIcon);
		statesIconArray.add(wvLabelIcon);
		statesIconArray.add(wiLabelIcon);
		statesIconArray.add(wyLabelIcon);
	}

	/**
	 * Set the name of each element in an array to that of a US state. Requires an array of 50 objects
	 * @param array
	 */
	private void assignStateNames(ArrayList<JLabel> array) {
		array.get(0).setName("AL");
		array.get(1).setName("AK");
		array.get(2).setName("AZ");
		array.get(3).setName("AR");
		array.get(4).setName("CA");
		array.get(5).setName("CO");
		array.get(6).setName("CT");
		array.get(7).setName("DE");
		array.get(8).setName("FL");
		array.get(9).setName("GA");
		array.get(10).setName("HI");
		array.get(11).setName("ID");
		array.get(12).setName("IL");
		array.get(13).setName("IN");
		array.get(14).setName("IA");
		array.get(15).setName("KS");
		array.get(16).setName("KY");
		array.get(17).setName("LA");
		array.get(18).setName("ME");
		array.get(19).setName("MD");
		array.get(20).setName("MA");
		array.get(21).setName("MI");
		array.get(22).setName("MN");
		array.get(23).setName("MS");
		array.get(24).setName("MO");
		array.get(25).setName("MT");
		array.get(26).setName("NE");
		array.get(27).setName("NV");
		array.get(28).setName("NH");
		array.get(29).setName("NJ");
		array.get(30).setName("NM");
		array.get(31).setName("NY");
		array.get(32).setName("NC");
		array.get(33).setName("ND");
		array.get(34).setName("OH");
		array.get(35).setName("OK");
		array.get(36).setName("OR");
		array.get(37).setName("PA");
		array.get(38).setName("RI");
		array.get(39).setName("SC");
		array.get(40).setName("SD");
		array.get(41).setName("TN");
		array.get(42).setName("TX");
		array.get(43).setName("UT");
		array.get(44).setName("VT");
		array.get(45).setName("VA");
		array.get(46).setName("WA");
		array.get(47).setName("WV");
		array.get(48).setName("WI");
		array.get(49).setName("WY");
	}

	/**
	 * Clear all icons from the statesIconArray
	 */
	public void clearEmptyStateIcons() {
		for (int i = 0; i < statesIconArray.size(); i++) {
			statesIconArray.get(i).setIcon(null);
		}
	}

	
	/**
	 * Revert all state images to a default white colour
	 */
	private void clearStateColours() {
		for (int i = 0; i < statesImgArray.size(); i++) {
			ImageIcon newicon = (ImageIcon) statesImgArray.get(i).getIcon();
			Image newimage = newicon.getImage();
			BufferedImage newbufferedimage = (BufferedImage) newimage;
			statesImgArray.get(i).setIcon(new ImageIcon(changeStateColour(newbufferedimage, Color.WHITE.getRGB())));
		}
	}

	private void updateIconsOnMap(TreeMap<String, ArrayList<Incident>> data) {

		// set default of each state to empty; set image if incident is found
		// within a state
		clearEmptyStateIcons();
		clearStateColours();
		// Store the total number of incidents
		int totalSize = incidentFetcher.getTotalIncidentsSize();
		// Iterate through each object in the treemap
		for (Map.Entry<String, ArrayList<Incident>> value : data.entrySet()) {
			int stateIncidentNumber = value.getValue().size();
			// compute the percentage of incidents in a state from the total
			int percentageOfIncidents = ((stateIncidentNumber * 100) / totalSize);
			for (int i = 0; i < statesImgArray.size(); i++) {
				if (statesImgArray.get(i).getName().equals(value.getKey())) {
					// Get the total number of incidents from the current array
					// of incidents within the selected treemap key

					//Assign a colour to a state depending on the overall percentage of incidents within that state
					if (percentageOfIncidents >= 4) {
						//Get an Icon from the JLabel, cast as an ImageIcon
						ImageIcon newicon = (ImageIcon) statesImgArray.get(i).getIcon();
						//Get the image from the ImageIcon
						Image newimage = newicon.getImage();
						//Cast image as a buffered image
						BufferedImage newbufferedimage = (BufferedImage) newimage;
						//get the array at i, and set am icon as a new ImageIcon using a buffered image after changing the states' image colour
						statesImgArray.get(i)
								.setIcon(new ImageIcon(changeStateColour(newbufferedimage, Color.RED.getRGB())));

					} else if ((percentageOfIncidents > 1) && (percentageOfIncidents < 4)) {

						ImageIcon newicon = (ImageIcon) statesImgArray.get(i).getIcon();
						Image newimage = newicon.getImage();
						BufferedImage newbufferedimage = (BufferedImage) newimage;
						statesImgArray.get(i)
								.setIcon(new ImageIcon(changeStateColour(newbufferedimage, Color.YELLOW.getRGB())));

					} else if ((percentageOfIncidents >= 0) && (percentageOfIncidents < 2)
							&& (stateIncidentNumber != 0)) {

						ImageIcon newicon = (ImageIcon) statesImgArray.get(i).getIcon();
						Image newimage = newicon.getImage();
						BufferedImage newbufferedimage = (BufferedImage) newimage;
						statesImgArray.get(i)
								.setIcon(new ImageIcon(changeStateColour(newbufferedimage, Color.GREEN.getRGB())));

					} else if (stateIncidentNumber == 0) {

						ImageIcon newicon = (ImageIcon) statesImgArray.get(i).getIcon();
						Image newimage = newicon.getImage();
						BufferedImage newbufferedimage = (BufferedImage) newimage;
						statesImgArray.get(i)
								.setIcon(new ImageIcon(changeStateColour(newbufferedimage, Color.WHITE.getRGB())));

					}

				}

			}

			// Iterate through the array of state labels
			for (int i = 0; i < statesIconArray.size(); i++) {
				// test if a state's label shares a name with that of the
				// current incidents key
				if (statesIconArray.get(i).getName().equals(value.getKey())) {
					if (percentageOfIncidents >= 4) {
						statesIconArray.get(i).setIcon(alienIconLarge);
						statesIconArray.get(i).setSize(32, 32);

					} else if ((percentageOfIncidents > 1) && (percentageOfIncidents < 4)) {
						statesIconArray.get(i).setIcon(alienIconMed);
						statesIconArray.get(i).setSize(24, 24);
					} else if ((percentageOfIncidents >= 0) && (percentageOfIncidents < 2)
							&& (stateIncidentNumber != 0)) {
						statesIconArray.get(i).setIcon(alienIconSmall);
						statesIconArray.get(i).setSize(16, 16);
					} else if (stateIncidentNumber == 0) {
						statesIconArray.get(i).setIcon(null);
					}

				}
			}
		}

	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		map = new ImageIcon("resources/MapPanelOverlay.png");
		g.drawImage(map.getImage(), 0, 0, null);
	}

	/**
	 * Method to change the red, green, yellow or white colour contents of an
	 * image to a new colour
	 * 
	 * @param oldimg
	 * @param newcolour
	 * @return
	 */
	public BufferedImage changeStateColour(BufferedImage oldimg, int newcolour) {
		if (oldimg == null) {
			System.out.println("Image was null, could not convert pixels");
		} else {
			for (int y = 0; y < oldimg.getHeight(); y++) {
				for (int w = 0; w < oldimg.getWidth(); w++) {
					if ((oldimg.getRGB(w, y) == Color.WHITE.getRGB()) || (oldimg.getRGB(w, y) == Color.RED.getRGB())
							|| (oldimg.getRGB(w, y) == Color.YELLOW.getRGB())
							|| (oldimg.getRGB(w, y) == Color.GREEN.getRGB()))
						oldimg.setRGB(w, y, newcolour);
				}
			}
		}
		return oldimg;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof MapIconsML && arg != null) {
			
			StateListPopupFrame temp = new StateListPopupFrame((ArrayList<Incident>) arg);
			//Get the name of the obversable, convert to a fullstring and set the title of the popup window
			String title = ((MapIconsML) o).getSourceName();
			title = convertToFullTitle(title);
			temp.setTitle(title);

			popups.add(temp);
		}
		if (o instanceof IncidentFetcher && (boolean) arg != true) {
			//Update icons on the map using an array of cincidents
			updateIconsOnMap(incidentFetcher.getIncidentsByState());

		}
		//Remove any null popups
		clearPopupsOfNulls();
	}

	/**
	 * Remove any empty/null objects within the popups array
	 */
	private void clearPopupsOfNulls() {
		if (popups.contains(null))
		{
			for (Object object : popups) {
				if (object == null) {
					popups.remove(object);
				}
			}
		}
	}

	/**
	 * Convert a provided string to a full state name based on it's abbreviation
	 * 
	 * @param title
	 * @return
	 */
	private String convertToFullTitle(String title) {
		switch (title) {
		case "AL": {
			title = "Alabama (" + title + ")";
			break;
		}

		case "AK": {
			title = "Alaska (" + title + ")";
			break;
		}

		case "AZ": {
			title = "Arizona (" + title + ")";
			break;
		}

		case "AR": {
			title = "Arkansas (" + title + ")";
			break;
		}

		case "CA": {
			title = "California (" + title + ")";
			break;
		}

		case "CO": {
			title = "Colorado (" + title + ")";
			break;
		}

		case "CT": {
			title = "Connecticut (" + title + ")";
			break;
		}

		case "DE": {
			title = "Delaware (" + title + ")";
			break;
		}

		case "DC": {
			title = "District of Columbia (" + title + ")";
			break;
		}

		case "FL": {
			title = "Florida (" + title + ")";
			break;
		}

		case "GA": {
			title = "Georgia (" + title + ")";
			break;
		}

		case "HI": {
			title = "Hawaii (" + title + ")";
			break;
		}

		case "ID": {
			title = "Idaho (" + title + ")";
			break;
		}

		case "IL": {
			title = "Illinois (" + title + ")";
			break;
		}

		case "IN": {
			title = "Indiana (" + title + ")";
			break;
		}

		case "IA": {
			title = "Iowa (" + title + ")";
			break;
		}

		case "KS": {
			title = "Kansas (" + title + ")";
			break;
		}

		case "KY": {
			title = "Kentucky (" + title + ")";
			break;
		}

		case "LA": {
			title = "Louisiana (" + title + ")";
			break;
		}

		case "ME": {
			title = "Maine (" + title + ")";
			break;
		}

		case "MD": {
			title = "Maryland (" + title + ")";
			break;
		}

		case "MA": {
			title = "Massachusetts (" + title + ")";
			break;
		}

		case "MI": {
			title = "Michigan (" + title + ")";
			break;
		}

		case "MN": {
			title = "Minnesota (" + title + ")";
			break;
		}

		case "MS": {
			title = "Mississippi (" + title + ")";
			break;
		}

		case "MO": {
			title = "Missouri (" + title + ")";
			break;
		}

		case "MT": {
			title = "Montana (" + title + ")";
			break;
		}

		case "NE": {
			title = "Nebraska (" + title + ")";
			break;
		}

		case "NV": {
			title = "Nevada (" + title + ")";
			break;
		}

		case "NH": {
			title = "New Hampshire (" + title + ")";
			break;
		}

		case "NJ": {
			title = "New Jersey (" + title + ")";
			break;
		}

		case "NM": {
			title = "New Mexico (" + title + ")";
			break;
		}

		case "NY": {
			title = "New York (" + title + ")";
			break;
		}

		case "NC": {
			title = "North Carolina (" + title + ")";
			break;
		}

		case "ND": {
			title = "North Dakota (" + title + ")";
			break;
		}

		case "OH": {
			title = "Ohio (" + title + ")";
			break;
		}

		case "OK": {
			title = "Oklahoma (" + title + ")";
			break;
		}

		case "OR": {
			title = "Oregon (" + title + ")";
			break;
		}

		case "PA": {
			title = "Pennsylvania (" + title + ")";
			break;
		}

		case "PR": {
			title = "Puerto Rico (" + title + ")";
			break;
		}

		case "RI": {
			title = "Rhode Island (" + title + ")";
			break;
		}

		case "SC": {
			title = "South Carolina (" + title + ")";
			break;
		}

		case "SD": {
			title = "South Dakota (" + title + ")";
			break;
		}

		case "TN": {
			title = "Tennessee (" + title + ")";
			break;
		}

		case "TX": {
			title = "Texas (" + title + ")";
			break;
		}

		case "UT": {
			title = "Utah (" + title + ")";
			break;
		}

		case "VT": {
			title = "Vermont (" + title + ")";
			break;
		}

		case "VA": {
			title = "Virginia (" + title + ")";
			break;
		}

		case "WA": {
			title = "Washington (" + title + ")";
			break;
		}

		case "WV": {
			title = "West Virginia (" + title + ")";
			break;
		}

		case "WI": {
			title = "Wisconsin (" + title + ")";
			break;
		}

		case "WY": {
			title = "Wyoming (" + title + ")";
			break;
		}
		}

		return title;
	}
}