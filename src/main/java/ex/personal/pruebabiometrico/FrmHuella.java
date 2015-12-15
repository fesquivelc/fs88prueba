/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ex.personal.pruebabiometrico;

import com.sun.jna.ptr.IntByReference;
import ex.personal.pruebabiometrico.fs88lib.FTRAnsiSDK;
import ex.personal.pruebabiometrico.fs88lib.FTRScanAPI;
import ex.personal.pruebabiometrico.gui.IconoHuella;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author Francis
 */
public class FrmHuella extends javax.swing.JFrame {

    private IconoHuella iconoHuella;
    private final FTRAnsiSDK ftrAnsiSDK = FTRAnsiSDK.INSTANCE;
    private final FTRScanAPI ftrScanAPI = FTRScanAPI.INSTANCE;
    private byte[] template;
    public boolean activo = false;

    public enum TipoOperacion {
        CAPTURAR, ENROLAR, IDENTIFICAR, VERIFICAR, CREAR_ANSI, CREAR_ISO
    }

    private class HiloOperacion implements Runnable {

        private final TipoOperacion operacion;
        private IntByReference equipo;
        private final FTRScanAPI.FTRSCAN_IMAGE_SIZE.ByReference imageSize;
        private final byte[] imagen;

        public HiloOperacion(TipoOperacion operacion) throws Exception {
            this.operacion = operacion;
            this.imageSize = new FTRScanAPI.FTRSCAN_IMAGE_SIZE.ByReference();
            this.equipo = ftrScanAPI.ftrScanOpenDevice();
            if (this.equipo == null) {
                throw new Exception("Equipo no detectado");
            }
            obtenerImageSize(this.equipo, this.imageSize);
            imagen = new byte[this.imageSize.nImageSize];
        }

        @Override
        public void run() {
            while (activo) {
                switch (operacion) {
                    case CAPTURAR:
                        obtenerCaptura();
                    case CREAR_ANSI:
                        obtenerANSI();
                }
            }
        }

        private void obtenerCaptura() {
            boolean captura = ftrAnsiSDK.ftrAnsiSdkCaptureImage(equipo, imagen);
            if (captura) {
                mostrarImagenHuella(imageSize.nWidth, imageSize.nHeight, imagen);
            }
        }

        private void obtenerImageSize(IntByReference equipo, FTRScanAPI.FTRSCAN_IMAGE_SIZE.ByReference imageSize) {
            ftrScanAPI.ftrScanGetImageSize(equipo, imageSize);
        }

        private byte[] obtenerANSI() {
            template = new byte[ftrAnsiSDK.ftrAnsiSdkGetMaxTemplateSize()];
            IntByReference templateSize = new IntByReference();
            boolean correcto = ftrAnsiSDK.ftrAnsiSdkCreateTemplate(equipo, (byte) 1, imagen, template, templateSize);
            if (correcto) {
                System.out.println("TEMPLATE SIZE: " + templateSize.getValue());
                activo = false;
                mostrarImagenHuella(imageSize.nWidth, imageSize.nHeight, imagen);
                txtMensaje.setText(arrayToHex(template));
                boolean res = ftrAnsiSDK.ftrAnsiSdkConvertAnsiTemplateToIso(template, null, templateSize);
                if(res){
                    System.out.println("EN ISO: "+templateSize.getValue());
                }
                return template;

            } else {
                System.out.println("NO SE PUDO OBTENER TEMPLATE");
                return null;
            }
        }

    }

    private String arrayToHex(byte[] template) {
        StringBuilder sb = new StringBuilder("");
        String formato = "%02X ";
        for(byte b : template){
            sb.append(String.format(formato, b));
        }
        return sb.toString();
    }

    public Image byteToImagen(int ancho, int alto, byte[] pBitmap) {
        BufferedImage hImage = new BufferedImage(ancho,
                alto,
                BufferedImage.TYPE_BYTE_GRAY);
        DataBuffer db1 = hImage.getRaster().getDataBuffer();
        for (int i = 0; i < db1.getSize(); i++) {
            db1.setElem(i, pBitmap[i]);
        }
        return hImage;
    }

    /**
     * Creates new form FrmHuella
     */
    public FrmHuella() {
        initComponents();
        iconoHuella = new IconoHuella(this.lblHuella.getPreferredSize().width, this.lblHuella.getPreferredSize().height);
        System.out.println("POR AQUI");
        iconoHuella.LoadImage("Futronic.JPG");
//        iconoHuella.setImage(createImageIcon("Futronic.JPG").getImage());
        this.lblHuella.setIcon(iconoHuella);
//        this.lblHuella.repaint();
    }

    private void mostrarImagenHuella(int nWidth, int nHeight, byte[] imagen) {
        iconoHuella.setImage(byteToImagen(nWidth, nHeight, imagen));
        lblHuella.repaint();
    }

    private ImageIcon createImageIcon(String path) {
        File fichero = new File(path);
        if (fichero.exists()) {
            return new ImageIcon(fichero.getAbsolutePath());
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        pnlPrincipal = new javax.swing.JPanel();
        pnlDatos = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        lblHuella = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtMensaje = new javax.swing.JTextArea();
        pnlHuella = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlPrincipal.setLayout(new java.awt.BorderLayout());

        pnlDatos.setLayout(new java.awt.GridBagLayout());

        jButton1.setText("Capturar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        pnlDatos.add(jButton1, gridBagConstraints);

        jButton2.setText("Detener");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        pnlDatos.add(jButton2, gridBagConstraints);

        lblHuella.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblHuella.setMaximumSize(new java.awt.Dimension(200, 200));
        lblHuella.setMinimumSize(new java.awt.Dimension(200, 200));
        lblHuella.setPreferredSize(new java.awt.Dimension(200, 200));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        pnlDatos.add(lblHuella, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        pnlDatos.add(lblEstado, gridBagConstraints);

        txtMensaje.setColumns(20);
        txtMensaje.setLineWrap(true);
        txtMensaje.setRows(5);
        jScrollPane1.setViewportView(txtMensaje);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        pnlDatos.add(jScrollPane1, gridBagConstraints);

        pnlPrincipal.add(pnlDatos, java.awt.BorderLayout.CENTER);

        pnlHuella.setLayout(new java.awt.GridBagLayout());
        pnlPrincipal.add(pnlHuella, java.awt.BorderLayout.EAST);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.activo = false;
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            // TODO add your handling code here:
            System.out.println("Por iniciar captura");
            this.activo = true;
            Thread realizarTarea = new Thread(new HiloOperacion(TipoOperacion.CREAR_ANSI));
            realizarTarea.start();

        } catch (Exception ex) {
            Logger.getLogger(FrmHuella.class.getName()).log(Level.SEVERE, null, ex);
            this.lblEstado.setText(ex.getMessage());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmHuella.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmHuella.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmHuella.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmHuella.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmHuella().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblHuella;
    private javax.swing.JPanel pnlDatos;
    private javax.swing.JPanel pnlHuella;
    private javax.swing.JPanel pnlPrincipal;
    private javax.swing.JTextArea txtMensaje;
    // End of variables declaration//GEN-END:variables
}
