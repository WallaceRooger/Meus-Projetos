package classes;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.mysql.cj.protocol.Resultset;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Tela_de_cadastro extends JFrame {

	private JPanel contentPane;
	private JTextField tfTelefone;
	private JTextField tfEmail;
	private JTextField tfNome;
	private JPasswordField pfSenha;
	private JTextField tfBusca;
	private JTable tbDados;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Tela_de_cadastro frame = new Tela_de_cadastro();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Tela_de_cadastro() {
		setResizable(false);
		setTitle("Tela de Cadastro");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 501, 530);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Nome");
		lblNewLabel.setBounds(10, 53, 49, 28);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Email");
		lblNewLabel_1.setBounds(10, 103, 82, 28);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Senha");
		lblNewLabel_2.setBounds(254, 156, 60, 35);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		contentPane.add(lblNewLabel_2);

		tfTelefone = new JTextField();
		tfTelefone.setBounds(98, 159, 134, 28);
		tfTelefone.setFont(new Font("Tahoma", Font.PLAIN, 16));
		contentPane.add(tfTelefone);
		tfTelefone.setColumns(10);

		tfEmail = new JTextField();
		tfEmail.setBounds(98, 105, 360, 28);
		tfEmail.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tfEmail.setColumns(10);
		contentPane.add(tfEmail);

		JLabel lblTelefone = new JLabel("Telefone");
		lblTelefone.setBounds(10, 157, 82, 28);
		lblTelefone.setFont(new Font("Tahoma", Font.BOLD, 16));
		contentPane.add(lblTelefone);

		tfNome = new JTextField();
		tfNome.setBounds(98, 59, 360, 28);
		tfNome.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tfNome.setColumns(10);
		contentPane.add(tfNome);

		pfSenha = new JPasswordField();
		pfSenha.setBounds(312, 159, 146, 28);
		pfSenha.setFont(new Font("Tahoma", Font.PLAIN, 16));
		contentPane.add(pfSenha);

		JLabel lblNewLabel_3 = new JLabel("Cadastrar Usu\u00E1rio");
		lblNewLabel_3.setBounds(144, 11, 174, 28);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 16));
		contentPane.add(lblNewLabel_3);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Buscar dados pelo Id", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(10, 410, 465, 73);
		contentPane.add(panel);
		panel.setLayout(null);

		tfBusca = new JTextField();
		tfBusca.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tfBusca.setBounds(117, 32, 111, 32);
		panel.add(tfBusca);
		tfBusca.setColumns(10);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if(tfBusca.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Informe um ID");
				}	else {



					try {

						Connection con = Conexao.faz_conexao();

						String sql = "SELECT * FROM usuario WHERE id = ?";

						PreparedStatement stmt = con.prepareStatement(sql);

						stmt.setString(1, tfBusca.getText());

						ResultSet rs = stmt.executeQuery();	

						while ( rs.next()) {
							tfNome.setText( rs.getString("nome"));
							tfEmail.setText( rs.getString("email"));
							pfSenha.setText( rs.getString("senha"));
							tfTelefone.setText( rs.getString("telefone"));

						}

						rs.close();
						con.close();

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		btnBuscar.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnBuscar.setBounds(10, 33, 97, 30);
		panel.add(btnBuscar);

		JButton btnListarDados = new JButton("Listar Dados");
		btnListarDados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {

					Connection con = Conexao.faz_conexao();

					String sql = "SELECT * FROM usuario";

					PreparedStatement stmt = con.prepareStatement(sql);

					ResultSet rs = stmt.executeQuery();

					DefaultTableModel modelo = (DefaultTableModel) tbDados.getModel();
					modelo.setNumRows(0);

					while(rs.next()) {

						modelo.addRow(new Object[] {rs.getString("id"), rs.getString("Nome"), rs.getString("Email"), 
								rs.getString("Senha"), rs.getString("Telefone")} );

					}

					rs.close();
					con.close();

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnListarDados.setBounds(322, 32, 133, 30);
		panel.add(btnListarDados);
		btnListarDados.setFont(new Font("Tahoma", Font.BOLD, 14));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 213, 463, 102);
		contentPane.add(scrollPane);

		tbDados = new JTable();
		tbDados.setModel(new DefaultTableModel(
				new Object[][] {
					{null, null, null, null, null},
				},
				new String[] {
						"Id", "Nome", "Email", "Senha", "Telefone"
				}
				) {
			boolean[] columnEditables = new boolean[] {
					true, false, true, true, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tbDados.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		scrollPane.setViewportView(tbDados);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBorder(new TitledBorder(null, "A\u00E7\u00F5es", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 326, 465, 73);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(21, 21, 106, 40);
		panel_1.add(btnSalvar);
		btnSalvar.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {

				if(tfNome.getText().equals("") || pfSenha.getText().equals("")) {

					JOptionPane.showMessageDialog(null,"Preencha todos os  campos!");
				}	else {



					try {

						Connection con = Conexao.faz_conexao();
						String sql = "insert into usuario(nome, email, senha, telefone) values (?,?,?,?)";

						PreparedStatement stmt = con.prepareStatement(sql);

						stmt.setString(1, tfNome.getText());
						stmt.setString(2, tfEmail.getText());
						stmt.setString(3, new String (pfSenha.getPassword()));
						stmt.setString(4, tfTelefone.getText());

						stmt.execute();

						stmt.close();
						con.close();

						JOptionPane.showMessageDialog(null,"Cadastro realizado com sucesso!");

						tfNome.setText("");
						tfEmail.setText("");
						tfTelefone.setText("");
						pfSenha.setText("");

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		btnSalvar.setFont(new Font("Tahoma", Font.BOLD, 16));

		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if(tfBusca.getText().equals("")) {

					JOptionPane.showMessageDialog(null, "Digite o Id.");

				}	else {

					try {

						Connection con = Conexao.faz_conexao();

						String sql = "UPDATE usuario SET   nome=?, email=?, senha=?, telefone=? WHERE id=?";

						PreparedStatement stmt = con.prepareStatement(sql);

						stmt.setString(1, tfNome.getText());
						stmt.setString(2, tfEmail.getText());
						stmt.setString(3, pfSenha.getText());
						stmt.setString(4, tfTelefone.getText());
						stmt.setString(5, tfBusca.getText());

						stmt.execute();
						stmt.close();
						con.close();

						JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		btnAtualizar.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnAtualizar.setBounds(168, 21, 124, 41);
		panel_1.add(btnAtualizar);

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if(tfBusca.getText().equals("")) {

					JOptionPane.showMessageDialog(null, "Informe o ID.");
				}	else {
					
				try {

					Connection con = Conexao.faz_conexao();

					String sql = "DELETE FROM usuario WHERE id = ?";

					PreparedStatement stmt = con.prepareStatement(sql);

					stmt.setString(1, tfBusca.getText());
					
					stmt.execute();

					stmt.close();
					
					con.close();

					JOptionPane.showMessageDialog(null, "Item Excluido!!");


				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	});
		btnExcluir.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnExcluir.setBounds(336, 21, 106, 40);
		panel_1.add(btnExcluir);
}
}
