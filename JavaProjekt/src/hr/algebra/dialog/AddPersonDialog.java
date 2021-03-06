/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.dialog;

import hr.algebra.model.Person;
import hr.algebra.utils.MessageUtils;
import hr.algebra.model.PersonManagable;

/**
 *
 * @author zakesekresa
 */
public class AddPersonDialog extends javax.swing.JDialog {

    private final PersonManagable personAddable;
    private final Person data;

    /**
     * Creates new form AddPersonDialog
     */
    public AddPersonDialog(java.awt.Frame parent, boolean modal, javax.swing.JPanel panelParent) {
        super(parent, modal);
        personAddable = (PersonManagable) panelParent;
        initComponents();
        data = new Person();
    }

    public AddPersonDialog(java.awt.Frame parent, boolean modal, javax.swing.JPanel panelParent, Person data) {
        super(parent, modal);
        personAddable = (PersonManagable) panelParent;
        initComponents();
        this.data = data;

        fillForm();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        tfFirstName = new javax.swing.JTextField();
        lbFirstNameError = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tfLastName = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("First name");

        lbFirstNameError.setForeground(java.awt.Color.red);

        jLabel3.setText("Last name");

        btnAdd.setText("Save");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tfFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbFirstNameError, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(tfLastName, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tfFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbFirstNameError, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAdd)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if (formValid()) {

            Person person = new Person(data.getId(), tfFirstName.getText().trim(), tfLastName.getText().trim());
            if (data.getId() == 0) {
                if (personAddable.addPerson(person)) {
                    dispose();
                } else {
                    MessageUtils.showErrorMessage("Error", "Cannot add author");
                }
            }
            else if (personAddable.updatePerson(person)) {
                dispose();
            } else {
                MessageUtils.showErrorMessage("Error", "Cannot update author");
            }

        }
    }//GEN-LAST:event_btnAddActionPerformed

    private boolean formValid() {
        boolean ok = true;

        ok &= !tfFirstName.getText().trim().isEmpty();
        lbFirstNameError.setText(tfFirstName.getText().trim().isEmpty() ? "X" : ""
        );
        return ok;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lbFirstNameError;
    private javax.swing.JTextField tfFirstName;
    private javax.swing.JTextField tfLastName;
    // End of variables declaration//GEN-END:variables

    private void fillForm() {
        tfFirstName.setText(data.getFirstName());
        tfLastName.setText(data.getLastName());
    }
}
