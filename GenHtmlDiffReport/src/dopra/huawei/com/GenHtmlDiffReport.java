package dopra.huawei.com;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.api.StashCreateCommand;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.tmatesoft.svn.core.ISVNLogEntryHandler;
import org.tmatesoft.svn.core.SVNAuthenticationException;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.wc.ISVNDiffStatusHandler;
import org.tmatesoft.svn.core.wc.ISVNOptions;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNDiffClient;
import org.tmatesoft.svn.core.wc.SVNDiffStatus;
import org.tmatesoft.svn.core.wc.SVNLogClient;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNStatusType;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

public class GenHtmlDiffReport {

	private JFrame frmhtml;
	private JTextField bcompath;
	private JTextField username;
	private JPasswordField password;
	private JComboBox svnAddrList;
	private JButton btnDelSvnAddr;
	private JButton genReportButton;

	private SVNLogClient log;
	private JTextField textsvnname;
	private JTextField textsvnaddr;
	private JSeparator separator_1;
	private JLabel lblsvn;
	private String pwd = "525[]252522fffs398*&^%ggg";
	private static PropertyHelper prophHelper = new PropertyHelper();
	private JTextPane txtpnTestTeset;
	private boolean bIsGenDiffing = false;
	private boolean isfailed = false;
	private JLabel label_5;
	private JTextField beginversiontext;
	private JLabel lblSvn_3;
	private JTextField endversiontext;
	private String beginver;
	private String endver;

	public synchronized void del(File file) {
	    File[] files = file.listFiles();
	    if (files != null)
	        for (File f : files)
	            del(f);
	    file.delete();
	}
	public synchronized void insert(String str) {
		SimpleAttributeSet attrset=new SimpleAttributeSet();
		//StyleConstants.setForeground(attrset, color);

		Document docs=txtpnTestTeset.getDocument();
		try{
		    docs.insertString(docs.getLength(), str, attrset); 
		    txtpnTestTeset.setCaretPosition(docs.getLength());
		}catch(BadLocationException ble){
		     System.out.println("BadLocationException:" + ble);
		     ble.printStackTrace();
		}
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GenHtmlDiffReport window = new GenHtmlDiffReport();
					window.frmhtml.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GenHtmlDiffReport() {
		initialize();
	}

	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmhtml = new JFrame();
		frmhtml.setTitle("\u751F\u6210\u5916\u90E8\u6587\u4EF6\u53D8\u66F4html\u6BD4\u8F83\u62A5\u544A\uFF08V0.0.1\uFF09");
		frmhtml.setBounds(100, 100, 934, 459);
		frmhtml.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmhtml.getContentPane().setLayout(null);

		txtpnTestTeset = new JTextPane();
		txtpnTestTeset.setText("\u4F7F\u7528\u672C\u8F6F\u4EF6\u524D\u8BF7\u5B89\u88C5\u597DBeyond Compare\u3001JRE(rtoos\u4E0A\u6709)\uFF1B\r\n\u9996\u6B21\u542F\u52A8\u53EF\u80FD\u9700\u8981\u5BF9 GenReport.jar\u70B9\u53F3\u952E\uFF0C\u4F9D\u6B21\uFF1A\u6253\u5F00\u65B9\u5F0F->\u9009\u62E9\u9ED8\u8BA4\u7A0B\u5E8F->Java(TM) Platform SE binary;\r\n\u4EE5\u540E\u542F\u52A8\u53EA\u9700\u76F4\u63A5\u53CC\u51FB GenReport.jar \u5373\u53EF\u8FD0\u884C\uFF1B\r\n\u9996\u6B21\u4F7F\u7528\u7531\u4E8E\u914D\u7F6E\u9519\u8BEF\u53EF\u80FD\u4F1A\u5931\u8D25\uFF0C\u5EFA\u8BAE\u5173\u95ED\u91CD\u542F\u5E94\u7528\uFF1B\r\n\u751F\u6210\u7684\u62A5\u544A\u5728\u5E94\u7528\uFF08GenReport.jar\uFF09\u6240\u5728\u76EE\u5F55\u7684\u95EE\u9898\u5355\u540D\u79F0\u6587\u4EF6\u5939\u4E2D\uFF1B");
		JScrollPane scroll = new JScrollPane(txtpnTestTeset); 
		scroll.setBounds(14, 280, 864, 125);
		frmhtml.getContentPane().add(scroll);
		
		bcompath = new JTextField();
		bcompath.setText(prophHelper.getKeyValue("BCOMPATH"));
		bcompath.setBounds(146, 129, 486, 21);
		frmhtml.getContentPane().add(bcompath);
		bcompath.setColumns(10);
		
		username = new JTextField();
		username.setText(prophHelper.getKeyValue("USERNAME"));
		username.setBounds(146, 63, 194, 21);
		frmhtml.getContentPane().add(username);
		username.setColumns(10);
		
		password = new JPasswordField();
		password.setText(pwd);
		password.setBounds(146, 98, 194, 21);
		frmhtml.getContentPane().add(password);
		
		genReportButton = new JButton("\u751F\u6210\u62A5\u544A");
		genReportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (bIsGenDiffing){
					JOptionPane.showMessageDialog(frmhtml.getContentPane(), "正在生成，请耐心等待！", "提示", JOptionPane.ERROR_MESSAGE);
					return ;
				}
				isfailed = false;
				beginver = beginversiontext.getText().trim();
				endver = endversiontext.getText().trim();
				if (beginver.length() == 0 || endver.length() == 0){
					JOptionPane.showMessageDialog(frmhtml.getContentPane(), "请输入起始版本和结束版本SVN号！", "提示", JOptionPane.ERROR_MESSAGE);
					return ;
				}
				txtpnTestTeset.setText("");

				del(new File("temp"));
				del(new File("change"));
				del(new File("GenReport"));
				try {
					File rootfile=new File("GenReport");
		    	    if(!rootfile.exists()){
	    				rootfile.mkdir();
	    				Runtime.getRuntime().exec(" attrib +H "+rootfile.getAbsolutePath());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				prophHelper.writeProperties("BCOMPATH", bcompath.getText());
				prophHelper.writeProperties("USERNAME", username.getText());
				
				if (password.getText().length() == 0){
					JOptionPane.showMessageDialog(frmhtml.getContentPane(),"请输入密码！", "提示", JOptionPane.ERROR_MESSAGE);
					return;
				}
				//insert("Generate html diffirent report is begin...\n");
				bIsGenDiffing = true;
				new Thread(){
				   public void run(){
						String svnurl = prophHelper.getKeyValue(svnAddrList.getSelectedItem().toString()+"REMOTECODEADDRESS").trim();
						SVNDiffClient diff;

						if (svnurl.length() == 0){
							JOptionPane.showMessageDialog(frmhtml.getContentPane(),"svn地址为空！", "提示", JOptionPane.ERROR_MESSAGE);
							return;
						}

						SVNRepositoryFactoryImpl.setup();
						ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
						SVNClientManager ourClientManager;
						if (pwd.equals(password.getText())){
							ourClientManager = SVNClientManager.newInstance((DefaultSVNOptions) options);
						} else{
							ourClientManager = SVNClientManager.newInstance(
									(DefaultSVNOptions) options, username.getText(), password.getText());
						}
						diff = ourClientManager.getDiffClient();
							
						GenDiff diffhandler = new GenDiff();
						try {
							diff.doDiffStatus(SVNURL.parseURIEncoded(svnurl), 
									SVNRevision.create(Long.parseLong(beginver)), SVNRevision.create(Long.parseLong(endver)), SVNRevision.HEAD,  
									SVNDepth.INFINITY, true, diffhandler);
						} catch (SVNAuthenticationException e) {
							e.printStackTrace();
							JOptionPane.showMessageDialog(frmhtml.getContentPane(),"比较报告生成失败！SVN用户名或密码错误", "提示", JOptionPane.ERROR_MESSAGE);
							return;
						} catch (Exception e) {
							e.printStackTrace();
							JOptionPane.showMessageDialog(frmhtml.getContentPane(),"比较报告生成失败！请检查相关配置", "提示", JOptionPane.ERROR_MESSAGE);
							return;
						}
						//insert("\nGenerate html diffirent report is end ...");
						if (!isfailed)
							JOptionPane.showMessageDialog(frmhtml.getContentPane(),"外部变更比较报告生成成功！", "提示", JOptionPane.ERROR_MESSAGE);
						bIsGenDiffing = false;
				   }
				}.start();
			}
		});
		genReportButton.setBounds(662, 100, 216, 50);
		frmhtml.getContentPane().add(genReportButton);
		
		JLabel label_1 = new JLabel("\u7528   \u6237  \u540D\uFF1A");
		label_1.setBounds(27, 66, 78, 15);
		frmhtml.getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("\u5BC6       \u7801\uFF1A");
		label_2.setBounds(27, 101, 109, 15);
		frmhtml.getContentPane().add(label_2);
		
		JLabel lblBeyondcompare = new JLabel("BeyondCompare\u8DEF\u5F84\uFF1A");
		lblBeyondcompare.setBounds(16, 132, 143, 15);
		frmhtml.getContentPane().add(lblBeyondcompare);
		
		JLabel lblNewLabel = new JLabel("\u8BF7\u9009\u62E9SVN\u4ED3\u5E93\uFF1A");
		lblNewLabel.setBounds(27, 32, 109, 15);
		frmhtml.getContentPane().add(lblNewLabel);
		
		btnDelSvnAddr = new JButton("\u5220\u9664");
		btnDelSvnAddr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nameString = textsvnname.getText();
				if (nameString.length() == 0){
					JOptionPane.showMessageDialog(frmhtml.getContentPane(),"请输入仓库名称！", "提示", JOptionPane.ERROR_MESSAGE);
				}
				prophHelper.removeProperties(nameString+"REMOTECODEADDRESS");
				svnAddrList.removeItem(nameString);
				JOptionPane.showMessageDialog(frmhtml.getContentPane(),"删除仓库地址成功！", "提示", JOptionPane.ERROR_MESSAGE);
			}
		});
		btnDelSvnAddr.setBounds(336, 197, 93, 23);
		frmhtml.getContentPane().add(btnDelSvnAddr);
		
		JLabel lblSvn = new JLabel("\u540D    \u79F0\uFF1A");
		lblSvn.setBounds(41, 201, 75, 15);
		frmhtml.getContentPane().add(lblSvn);
		
		JLabel lblSvn_1 = new JLabel("SVN\u4ED3\u5E93\u5730\u5740\uFF1A");
		lblSvn_1.setBounds(27, 238, 89, 15);
		frmhtml.getContentPane().add(lblSvn_1);
		
		textsvnname = new JTextField();
		textsvnname.setBounds(147, 198, 167, 21);
		frmhtml.getContentPane().add(textsvnname);
		textsvnname.setColumns(10);
		
		textsvnaddr = new JTextField();
		textsvnaddr.setBounds(146, 235, 476, 21);
		frmhtml.getContentPane().add(textsvnaddr);
		textsvnaddr.setColumns(10);
		
		JButton btnAddSvnAddr = new JButton("\u589E\u52A0");
		btnAddSvnAddr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nameString = textsvnname.getText().trim();
				String svnaddrString = textsvnaddr.getText().trim();
				if (nameString.length() == 0){
					JOptionPane.showMessageDialog(frmhtml.getContentPane(),"请输入仓库名称！", "提示", JOptionPane.ERROR_MESSAGE);
					return;
				}
				prophHelper.writeProperties(nameString+"REMOTECODEADDRESS", svnaddrString);
				svnAddrList.removeItem(nameString);
				svnAddrList.addItem(nameString);
				svnAddrList.setSelectedItem(nameString);
				JOptionPane.showMessageDialog(frmhtml.getContentPane(),"增加仓库地址成功！", "提示", JOptionPane.ERROR_MESSAGE);
			}
		});
		btnAddSvnAddr.setBounds(439, 197, 93, 23);
		frmhtml.getContentPane().add(btnAddSvnAddr);
		
		JSeparator separator = new JSeparator();
		separator.setToolTipText("");
		separator.setBounds(16, 160, 864, 2);
		frmhtml.getContentPane().add(separator);
		
		separator_1 = new JSeparator();
		separator_1.setToolTipText("");
		separator_1.setBounds(16, 185, 864, 2);
		frmhtml.getContentPane().add(separator_1);
		
		lblsvn = new JLabel("\u914D \u7F6E SVN \u4ED3 \u5E93");
		lblsvn.setBounds(383, 163, 115, 15);
		frmhtml.getContentPane().add(lblsvn);
		
		svnAddrList = new JComboBox();
		svnAddrList.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED){
					if (svnAddrList.getSelectedItem() != null){
						String svnname = svnAddrList.getSelectedItem().toString();
						textsvnname.setText(svnname);
						textsvnaddr.setText(prophHelper.getKeyValue(svnname+"REMOTECODEADDRESS"));
					}
				}
			}
		});
		Set<Object> allkeyclone = new HashSet<Object>();
		allkeyclone.addAll(prophHelper.getProperties().keySet());
		allkeyclone.remove("BCOMPATH");
		allkeyclone.remove("USERNAME");
		String temp;
    	for (Object o : allkeyclone){
    		temp = o.toString().replaceAll("REMOTECODEADDRESS", "");
    		svnAddrList.removeItem(temp);
    		svnAddrList.addItem(temp);
    		svnAddrList.setSelectedItem(temp);
    	}

		svnAddrList.setBounds(146, 25, 168, 28);
		frmhtml.getContentPane().add(svnAddrList);
		
		label_5 = new JLabel("\u53EA\u9700\u9996\u6B21\u8F93\u5165\uFF0C\u7CFB\u7EDF\u81EA\u52A8\u8BB0\u4F4F\u5BC6\u7801");
		label_5.setBounds(350, 101, 226, 15);
		frmhtml.getContentPane().add(label_5);
		
		JLabel lblhttp = new JLabel("\u5FC5\u987B\u662Fhttp\u5F00\u5934\u7684\u5730\u5740");
		lblhttp.setBounds(648, 238, 159, 15);
		frmhtml.getContentPane().add(lblhttp);
		
		JLabel lblSvn_2 = new JLabel("SVN\u8D77\u59CB\u7248\u672C\u53F7\uFF1A");
		lblSvn_2.setBounds(662, 28, 109, 15);
		frmhtml.getContentPane().add(lblSvn_2);
		
		beginversiontext = new JTextField();
		beginversiontext.setColumns(10);
		beginversiontext.setBounds(774, 25, 104, 21);
		frmhtml.getContentPane().add(beginversiontext);
		
		lblSvn_3 = new JLabel("SVN\u7ED3\u675F\u7248\u672C\u53F7\uFF1A");
		lblSvn_3.setBounds(662, 56, 109, 15);
		frmhtml.getContentPane().add(lblSvn_3);
		
		endversiontext = new JTextField();
		endversiontext.setColumns(10);
		endversiontext.setBounds(774, 53, 104, 21);
		frmhtml.getContentPane().add(endversiontext);
	}

	class GenDiff implements ISVNDiffStatusHandler{
		private SVNUpdateClient updateClient;
		private HashSet<String> filelist = new HashSet<String>();
		StringBuilder cmd = new StringBuilder();
		private Runtime rt = Runtime.getRuntime();
		private String reg =".*/src/.*/src/.*";  //判断字符串中是否含有两次/src/
		//String ordernoString = orderno.getText().trim();

		public GenDiff(){
			SVNRepositoryFactoryImpl.setup();
			ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
			SVNClientManager ourClientManager;
			if (pwd.equals(password.getText())){
				ourClientManager = SVNClientManager.newInstance((DefaultSVNOptions) options);
			} else{
				ourClientManager = SVNClientManager.newInstance(
						(DefaultSVNOptions) options, username.getText(), password.getText());
			}
			updateClient = ourClientManager.getUpdateClient();
		}

		public void handleDiffStatus(SVNDiffStatus diffStatus){
			File wcDir;
			String filefullnameString = diffStatus.getPath();

			if ( filefullnameString.endsWith(".h") || filefullnameString.endsWith(".def") ){

			    if (filefullnameString.matches(reg) || filefullnameString.contains("dopra")){
			    	return;
			    }

				SVNStatusType statusType = diffStatus.getModificationType();
				try {
					SVNURL url = diffStatus.getURL(); 
					filelist.add(url.toString());
					if (statusType == SVNStatusType.STATUS_ADDED){
						insert("\nadded: " + filefullnameString);
						return;
					}
					if (statusType == SVNStatusType.STATUS_DELETED){
						insert("\ndeleted: " + filefullnameString);
						return;
					}
					wcDir = new File("GenReport/old/"+ filefullnameString);
					updateClient.doExport(url, wcDir, SVNRevision.HEAD, SVNRevision.create(Long.parseLong(beginver)), "", true, SVNDepth.INFINITY);
					wcDir = new File("GenReport/new/" + filefullnameString);
					updateClient.doExport(url, wcDir, SVNRevision.HEAD, SVNRevision.create(Long.parseLong(endver)), "", true, SVNDepth.INFINITY);
					cmd.setLength(0);
					cmd.append(bcompath.getText()).append("/BCompare.exe /silent @report.ini  GenReport/old/")
						.append(filefullnameString).append(" GenReport/new/").append(filefullnameString)
						.append(" change/").append(filefullnameString.replace('/', '-')).append(".html");
					rt.exec(cmd.toString());
					//insert("\ngen report success: " + filefullnameString);
				} catch (Exception e) {
					insert("\n" + filefullnameString + "(status:" + statusType.toString() + ")" + " failed failed failed failed...");
					e.printStackTrace();
					isfailed = true;
					JOptionPane.showMessageDialog(frmhtml.getContentPane(),"比较报告生成失败！", "提示", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
}
