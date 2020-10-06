
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

public class JF_Principal extends javax.swing.JFrame {
    
    private BufferedImage bufferImage, bf;
    private String dir;
    
    public JF_Principal() {
        initComponents();
        
        selecionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    carregarImagem();
                } catch (IOException ex) {
                    
                }
            }
        });
        redimensionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String largura = JOptionPane.showInputDialog(JF_Principal.this, "Defina a nova largura:");
                String altura = JOptionPane.showInputDialog(JF_Principal.this, "Defina a nova altura:");
                try {
                    BufferedImage img = new Transformadores(bufferImage).redimensionar(Integer.parseInt(altura), Integer.parseInt(largura));
                    setBf(img);
                    imagem.setIcon(new ImageIcon(img));
                    infoImagens(img);
                } catch (NullPointerException | NumberFormatException e) {
                    JOptionPane.showMessageDialog(JF_Principal.this, "Insira as imagens e/ou os dados corretamente", "Mensagem de erro", ERROR_MESSAGE);
                    
                }
                
            }
        });
        
        espelhar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String[] selecione = {"HORIZONTAL", "VERTICAL"};
                int opt = JOptionPane.showOptionDialog(JF_Principal.this, "Selecione o tipo de espelhamento:", " ", 1, 3, null, selecione, null);
                try {
                    BufferedImage img = new Transformadores(bufferImage).espelhamento(opt);
                    setBf(img);
                    imagem.setIcon(new ImageIcon(img));
                    infoImagens(img);
                } catch (NullPointerException | NumberFormatException e) {
                    JOptionPane.showMessageDialog(JF_Principal.this, "Insira as imagens e/ou os dados corretamente", "Mensagem de erro", ERROR_MESSAGE);
                    
                }
            }
        });
        deslocar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String x = JOptionPane.showInputDialog(null, "Digite deslocamento eixo x:");
                String y = JOptionPane.showInputDialog(null, "Digite deslocamento eixo y:");
                try {
                    BufferedImage img = new Transformadores(bufferImage).deslocamento(Integer.parseInt(x), Integer.parseInt(y));
                    setBf(img);
                    imagem.setIcon(new ImageIcon(img));
                    infoImagens(img);
                } catch (NullPointerException | NumberFormatException e) {
                    JOptionPane.showMessageDialog(JF_Principal.this, "Insira as imagens e/ou os dados corretamente", "Mensagem de erro", ERROR_MESSAGE);
                    
                }
            }
        });
        rotacionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String[] opt = {"ESQUERDA", "DIREITA"};
                int opcao = JOptionPane.showOptionDialog(JF_Principal.this, "Selecione o lado da rotação:", " ", 1, 3, null, opt, null);
                String grau_rotacao = JOptionPane.showInputDialog(JF_Principal.this, "Digite quantos graus de rotação:");
                try {
                    BufferedImage img = new Transformadores(bufferImage).rotacionar(Integer.parseInt(grau_rotacao), opcao);
                    setBf(img);
                    imagem.setIcon(new ImageIcon(img));
                    infoImagens(img);
                } catch (NullPointerException | NumberFormatException e) {
                    JOptionPane.showMessageDialog(JF_Principal.this, "Insira as imagens e/ou os dados corretamente", "Mensagem de erro", ERROR_MESSAGE);
                    
                }
                
            }
        });
        
        filtroSobel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    BufferedImage img = new Transformadores(bufferImage).filtroSobel();
                    setBf(img);
                    imagem.setIcon(new ImageIcon(img));
                    infoImagens(img);
                } catch (NullPointerException | NumberFormatException ex) {
                    JOptionPane.showMessageDialog(JF_Principal.this, "Insira as imagens e/ou os dados corretamente", "Mensagem de erro", ERROR_MESSAGE);
                    
                }
            }
        });
        negativo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    BufferedImage img = new Transformadores(bufferImage).negativo();
                    setBf(img);
                    imagem.setIcon(new ImageIcon(img));
                    infoImagens(img);
                } catch (NullPointerException | NumberFormatException ex) {
                    JOptionPane.showMessageDialog(JF_Principal.this, "Insira as imagens e/ou os dados corretamente", "Mensagem de erro", ERROR_MESSAGE);
                    
                }
            }
        });
        brilho.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String brilho = JOptionPane.showInputDialog(JF_Principal.this, "Defina o brilho da imagem:");
                try {
                    BufferedImage img = new Transformadores(bufferImage).brilho(Integer.parseInt(brilho));
                    setBf(img);
                    imagem.setIcon(new ImageIcon(img));
                    infoImagens(img);
                } catch (NullPointerException | NumberFormatException ex) {
                    JOptionPane.showMessageDialog(JF_Principal.this, "Insira as imagens e/ou os dados corretamente", "Mensagem de erro", ERROR_MESSAGE);
                    
                }
            }
        });
        filtroGauss.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    BufferedImage img = new Transformadores(bufferImage).filtroGauss();
                    setBf(img);
                    imagem.setIcon(new ImageIcon(img));
                    infoImagens(img);
                } catch (NullPointerException | NumberFormatException ex) {
                    JOptionPane.showMessageDialog(JF_Principal.this, "Insira as imagens e/ou os dados corretamente", "Mensagem de erro", ERROR_MESSAGE);
                    
                }
            }
        });
        filtroMediana.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    BufferedImage img = new Transformadores(bufferImage).filtroMediana();
                    setBf(img);
                    imagem.setIcon(new ImageIcon(img));
                    infoImagens(img);
                } catch (NullPointerException | NumberFormatException ex) {
                    JOptionPane.showMessageDialog(JF_Principal.this, "Insira as imagens e/ou os dados corretamente", "Mensagem de erro", ERROR_MESSAGE);
                    
                }
            }
        });
        contraste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String contraste = JOptionPane.showInputDialog(JF_Principal.this, "Defina o contraste da imagem:");
                try {
                    BufferedImage img = new Transformadores(bufferImage).brilho(Integer.parseInt(contraste));
                    setBf(img);
                    imagem.setIcon(new ImageIcon(img));
                    infoImagens(img);
                } catch (NullPointerException | NumberFormatException ex) {
                    JOptionPane.showMessageDialog(JF_Principal.this, "Insira as imagens e/ou os dados corretamente", "Mensagem de erro", ERROR_MESSAGE);
                    
                }
            }
        });
        
        salvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Salvar imagem...");
                int opcao = fileChooser.showSaveDialog(null);
                if (opcao == JFileChooser.APPROVE_OPTION) {
                    File arquivo = fileChooser.getSelectedFile();
                    try {
                        ImageIO.write((RenderedImage) getBf(), "png", new File(arquivo.getAbsolutePath() + ".png"));
                    } catch (IOException ex) {
                        
                    }
                    
                }
            }
        });
        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        imagem = new javax.swing.JLabel();
        labelInfo = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        selecionar = new javax.swing.JMenuItem();
        salvar = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        redimensionar = new javax.swing.JMenuItem();
        espelhar = new javax.swing.JMenuItem();
        deslocar = new javax.swing.JMenuItem();
        rotacionar = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        filtroGauss = new javax.swing.JMenuItem();
        filtroMediana = new javax.swing.JMenuItem();
        filtroSobel = new javax.swing.JMenuItem();
        brilho = new javax.swing.JMenuItem();
        contraste = new javax.swing.JMenuItem();
        negativo = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PROCESSAMENTO DIGITAL DE IMAGEM");
        setPreferredSize(new java.awt.Dimension(1366, 768));

        imagem.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        labelInfo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jMenu1.setText("Arquivo");

        selecionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/envio.png"))); // NOI18N
        selecionar.setText("Selecionar Arquivo");
        jMenu1.add(selecionar);

        salvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/salve-.png"))); // NOI18N
        salvar.setText("Salvar Arquivo");
        jMenu1.add(salvar);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Transformação geométrica");

        redimensionar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.ALT_MASK));
        redimensionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/redimensionar.png"))); // NOI18N
        redimensionar.setText("Redimensionar");
        jMenu2.add(redimensionar);

        espelhar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.ALT_MASK));
        espelhar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/giro.png"))); // NOI18N
        espelhar.setText("Espelhar");
        jMenu2.add(espelhar);

        deslocar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.ALT_MASK));
        deslocar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/deslocamento.png"))); // NOI18N
        deslocar.setText("Deslocar");
        jMenu2.add(deslocar);

        rotacionar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.ALT_MASK));
        rotacionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/repeticao.png"))); // NOI18N
        rotacionar.setText("Rotacionar");
        jMenu2.add(rotacionar);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Segmentação de imagens");

        filtroGauss.setText("Filtro Gauss");
        jMenu3.add(filtroGauss);

        filtroMediana.setText("Filtro Mediana");
        jMenu3.add(filtroMediana);

        filtroSobel.setText("Filtro Sobel");
        jMenu3.add(filtroSobel);

        brilho.setText("Brilho");
        jMenu3.add(brilho);

        contraste.setText("Contraste");
        jMenu3.add(contraste);

        negativo.setText("Negativo");
        jMenu3.add(negativo);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(imagem, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 859, Short.MAX_VALUE)
                    .addComponent(labelInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(labelInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(imagem, javax.swing.GroupLayout.PREFERRED_SIZE, 531, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(885, 636));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            
            System.out.println("Erro: " + e.getMessage());
            
        }
        
        java.awt.EventQueue.invokeLater(() -> {
            new JF_Principal().setVisible(true);
        });
    }
    
    public void carregarImagem() throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(null);
        File f = fileChooser.getSelectedFile();
        String path = f.getAbsolutePath();
        this.dir = path;
        bufferImage = ImageIO.read(f);
        infoImagens(bufferImage);
        imagem.setIcon(new ImageIcon(bufferImage));
    }
    
    public void infoImagens(BufferedImage img) {
        int altura = img.getHeight();
        int largura = img.getWidth();
        String d = dir;
        File f = new File(d);
        double kylobytes = f.length() / 1024;
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        labelInfo.setText("Informações da imagem: Altura: " + altura + " - " + "Largura: " + largura + " / Caminho da Imagem: " + d + " / Tamanho da imagem: " + decimalFormat.format(kylobytes) + " KB");
    }
    
    public BufferedImage getBf() {
        return bf;
    }
    
    public void setBf(BufferedImage bf) {
        this.bf = bf;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem brilho;
    private javax.swing.JMenuItem contraste;
    private javax.swing.JMenuItem deslocar;
    private javax.swing.JMenuItem espelhar;
    private javax.swing.JMenuItem filtroGauss;
    private javax.swing.JMenuItem filtroMediana;
    private javax.swing.JMenuItem filtroSobel;
    private javax.swing.JLabel imagem;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JLabel labelInfo;
    private javax.swing.JMenuItem negativo;
    private javax.swing.JMenuItem redimensionar;
    private javax.swing.JMenuItem rotacionar;
    private javax.swing.JMenuItem salvar;
    private javax.swing.JMenuItem selecionar;
    // End of variables declaration//GEN-END:variables
}
