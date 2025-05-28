/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Vista;
import Controlador.CompraControlador;
import Modelo.Compra;
import Controlador.DetalleCompraControlador;
import Modelo.DetalleCompra;
import Controlador.EmpleadoControlador;
import Modelo.Empleado;
import Controlador.ProductoControlador;
import Modelo.Producto;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import java.util.Date;
import java.util.TimeZone;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.Timer;
/**
 *
 * @author Gena
 */
public class VistaCompras extends javax.swing.JPanel {

    /**
     * Creates new form vistaCompras
     */
    public VistaCompras() {
        initComponents();
        this.compraControlador = new CompraControlador();
        this.detalleCompraControlador = new DetalleCompraControlador();
        this.empleadoControlador = new EmpleadoControlador();
        this.productoControlador = new ProductoControlador();
        eventoComboProductos();

        selectorfechaCompra.setDate(new Date());
        ((JTextField) selectorfechaCompra.getDateEditor().getUiComponent()).setEditable(false);

        // Limpiar las filas vacías de tablaDetalles
        DefaultTableModel modelDetalles = (DefaultTableModel) tablaDetalles.getModel();
        modelDetalles.setRowCount(0);

        cargarDatosTablaCompras();
        cargarEmpleados();
        cargarProductos();
        actualizarHora();
    }
    
    private final CompraControlador compraControlador;
    private final DetalleCompraControlador detalleCompraControlador;
    private final EmpleadoControlador empleadoControlador;
    private final ProductoControlador productoControlador;

    private Integer idEmpleadoSeleccionado = null;
    private Integer idProductoSeleccionado = null;

    private Timer timer; // Variable de instancia para el Timer
    private boolean horabd = false;

    private void limpiar() {
        textBuscar.setText("");
        idEmpleadoSeleccionado = null;
        selectorfechaCompra.setDate(new Date());

        // Limpiar la tabla de detalles
        tablaDetalles.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"ID Producto", "Producto", "Precio Unitario", "Cantidad", "Subtotal"}));

        cargarDatosTablaCompras();
        cargarEmpleados();
        cargarProductos();

        btnEliminar.setEnabled(true);
        btnGuardar.setEnabled(true);

        horabd = false; // Restablece para mostrar hora actual
        actualizarHora(); // Vuelve a iniciar el timer
    }
    
    private void actualizarHora() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("America/Managua"));

        // Detener el timer anterior si existe
        if (timer != null) {
            timer.stop();
        }

        if (horabd) {
            return; // No actualiza la hora si está cargada desde la base de datos
        }

        timer = new Timer(1000, e -> {
            Date now = new Date();
            hora.setText(sdf.format(now));
        });
        timer.start();
    }
    
    private void cargarDatosTablaCompras() {
        List<Compra> compras = compraControlador.obtenerTodasCompras();

        if (compras != null) {

            DefaultTableModel model = (DefaultTableModel) tablaCompras.getModel();
            model.setRowCount(0);

            for (Compra c : compras) {

                Empleado empleado = empleadoControlador.obtenerEmpleadoPorId(c.getIdEmpleado());
                String nombreEmpleado = empleado.getPrimerNombre() + " " + empleado.getPrimerApellido();

                Object[] row = {
                    c.getIdCompra(),
                    c.getFechaCompra(),
                    nombreEmpleado,
                    c.getTotalCompra()
                };
                model.addRow(row);
            }
        }
    }

    private void cargarEmpleados() {
        try {
            // Obtener las categorías desde el controlador
            List<Empleado> empleados = empleadoControlador.obtenerTodosEmpleados();

            // Limpiar el combo box por si tiene datos
            comboEmpleados.removeAllItems();

            // Agregar cada categoría al combo box
            for (Empleado e : empleados) {
                comboEmpleados.addItem(e.getPrimerNombre() + " " + e.getPrimerApellido()); // Mostrar el nombre
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar los empleados: " + e.getMessage());
        }
    }

    private void cargarProductos() {
        try {
            // Obtener las categorías desde el controlador
            List<Producto> productos = productoControlador.obtenerTodosProductos();

            // Limpiar el combo box por si tiene datos
            comboProductos.removeAllItems();

            // Agregar cada categoría al combo box
            for (Producto p : productos) {
                comboProductos.addItem(p.getNombreProducto()); // Mostrar el nombre
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar los productos: " + e.getMessage());
        }
    }

    private void eventoComboProductos() {
        comboProductos.addActionListener(e -> {
            // Obtener el índice seleccionado
            int indiceSeleccionado = comboProductos.getSelectedIndex();

            if (indiceSeleccionado >= 0) { // Verificar que se haya seleccionado algo
                try {
                    // Obtener la lista de categorías desde el controlador o memoria
                    List<Producto> productos = productoControlador.obtenerTodosProductos();

                    // Obtener el objeto de categoría correspondiente al índice seleccionado
                    Producto productoSeleccionado = productos.get(indiceSeleccionado);

                    // Actualizar la variable global con el ID de la categoría seleccionada
                    idProductoSeleccionado = productoSeleccionado.getIdProducto();

                    // Actualizar el campo textPrecio con el precio unitario del producto
                    textPrecio.setText(String.valueOf(productoSeleccionado.getPrecioUnitario()));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error al seleccionar categoría: " + ex.getMessage());
                }
            }
        });
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        comboEmpleados = new javax.swing.JComboBox<>();
        selectorfechaCompra = new com.toedter.calendar.JDateChooser();
        comboProductos = new javax.swing.JComboBox<>();
        textPrecio = new javax.swing.JTextField();
        textCantidad = new javax.swing.JTextField();
        btnAgregar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaDetalles = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        textBuscar = new javax.swing.JTextField();
        btnQuitarDetalle = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaCompras = new javax.swing.JTable();
        btnActualizar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        hora = new javax.swing.JLabel();

        jLabel1.setText("Empleado");

        jLabel2.setText("Fecha");

        jLabel3.setText("Producto");

        jLabel4.setText("Precio");

        jLabel5.setText("Cantidad");

        comboEmpleados.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        comboProductos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboProductosActionPerformed(evt);
            }
        });

        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accionBotonAgregar(evt);
            }
        });

        tablaDetalles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID Producto", "Producto", "Precio Unitario", "Cantidad", "Subtotal"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Float.class, java.lang.Integer.class, java.lang.Float.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaDetalles);

        jLabel6.setText("Buscar");

        textBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textBuscarKeyTyped(evt);
            }
        });

        btnQuitarDetalle.setText("Quitar Detalle");
        btnQuitarDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accionBotonQuitarDetalle(evt);
            }
        });

        tablaCompras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID Compra", "Fecha/Hora", "Empleado", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Float.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaCompras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaComprasMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablaCompras);

        btnActualizar.setText("ActualizarCompra");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accionBotonActualizar(evt);
            }
        });

        btnEliminar.setText("Eliminar Compra");

        btnGuardar.setText("Guardar Compra");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accionBotonGuardar(evt);
            }
        });

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accionBotonLimpiar(evt);
            }
        });

        hora.setText("00:00:00");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel6)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(textBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnQuitarDetalle))
                        .addComponent(jScrollPane1)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(comboEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1))
                            .addGap(12, 12, 12)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2)
                                .addComponent(selectorfechaCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(12, 12, 12)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(comboProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(hora)))
                            .addGap(12, 12, 12)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel4)
                                .addComponent(textPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(textCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel5)))
                        .addComponent(jScrollPane2))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnLimpiar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnActualizar)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(hora))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(comboEmpleados)
                            .addComponent(selectorfechaCompra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(textCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(textPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(comboProductos))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(textBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnQuitarDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(249, 249, 249))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void accionBotonAgregar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accionBotonAgregar
        // TODO add your handling code here:
         try {
            // Obtener el índice seleccionado del comboProductos
            int indiceSeleccionado = comboProductos.getSelectedIndex();
            if (indiceSeleccionado < 0) {
                JOptionPane.showMessageDialog(this, "Seleccione un producto.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener la lista de productos
            List<Producto> productos = productoControlador.obtenerTodosProductos();
            Producto productoSeleccionado = productos.get(indiceSeleccionado);

            // Obtener el precio unitario del producto
            float precioUnitario = productoSeleccionado.getPrecioUnitario();

            // Obtener la cantidad ingresada
            String cantidadStr = textCantidad.getText().trim();
            if (cantidadStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese una cantidad.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int cantidad;
            try {
                cantidad = Integer.parseInt(cantidadStr);
                if (cantidad <= 0) {
                    JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser un número entero válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Calcular el subtotal
            float subtotal = precioUnitario * cantidad;

            // Agregar los datos a la tabla tablaDetalles
            DefaultTableModel model = (DefaultTableModel) tablaDetalles.getModel();
            Object[] row = {
                productoSeleccionado.getIdProducto(),
                productoSeleccionado.getNombreProducto(),
                precioUnitario,
                cantidad,
                subtotal
            };
            model.addRow(row);

            // Limpiar los campos después de agregar
            textCantidad.setText("");
            textPrecio.setText("");
            cargarProductos();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al agregar el producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_accionBotonAgregar

    private void comboProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboProductosActionPerformed
        // TODO add your handling code here:
        comboProductos.addActionListener(e -> {
            // Obtener el índice seleccionado
            int indiceSeleccionado = comboProductos.getSelectedIndex();

            if (indiceSeleccionado >= 0) { // Verificar que se haya seleccionado algo
                try {
                    // Obtener la lista de categorías desde el controlador o memoria
                    List<Producto> productos = productoControlador.obtenerTodosProductos();

                    // Obtener el objeto de categoría correspondiente al índice seleccionado
                    Producto productoSeleccionado = productos.get(indiceSeleccionado);

                    // Actualizar la variable global con el ID de la categoría seleccionada
                    idProductoSeleccionado = productoSeleccionado.getIdProducto();

                    // Actualizar el campo textPrecio con el precio unitario del producto
                    textPrecio.setText(String.valueOf(productoSeleccionado.getPrecioUnitario()));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error al seleccionar categoría: " + ex.getMessage());
                }
            }
        });
    }//GEN-LAST:event_comboProductosActionPerformed

    private void accionBotonGuardar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accionBotonGuardar
        // TODO add your handling code here:
        try {
            // Obtener el índice seleccionado de empleados
            int indiceEmpleado = comboEmpleados.getSelectedIndex();
            if (indiceEmpleado < 0) {
                JOptionPane.showMessageDialog(this, "Seleccione un empleado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener la lista de  empleados
            List<Empleado> empleados = empleadoControlador.obtenerTodosEmpleados();
            int idEmpleado = empleados.get(indiceEmpleado).getIdEmpleado();

            // Obtener la fecha seleccionada
            Date fechaVenta = selectorfechaCompra.getDate();
            if (fechaVenta == null) {
                JOptionPane.showMessageDialog(this, "Seleccione una fecha.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener los detalles de la tabla tablaDetalles
            DefaultTableModel modelDetalles = (DefaultTableModel) tablaDetalles.getModel();
            int rowCount = modelDetalles.getRowCount();
            if (rowCount == 0) {
                JOptionPane.showMessageDialog(this, "Agregue al menos un producto a la compra.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear lista de detalles y calcular total
            List<DetalleCompra> detalles = new ArrayList<>();
            float totalVenta = 0;
            for (int i = 0; i < rowCount; i++) {
                int idProducto = (int) modelDetalles.getValueAt(i, 0); // ID Producto como Integer
                float precioUnitario = ((Number) modelDetalles.getValueAt(i, 2)).floatValue(); // Precio Unitario como Float
                int cantidad = (int) modelDetalles.getValueAt(i, 3); // Cantidad como Integer
                float subtotal = ((Number) modelDetalles.getValueAt(i, 4)).floatValue(); // Subtotal como Float

                // Crear objeto DetalleCompra
                DetalleCompra detalle = new DetalleCompra();
                detalle.setIdProducto(idProducto);
                detalle.setCantidad(cantidad);
                detalle.setPrecioUnitario(precioUnitario);
                detalles.add(detalle);

                totalVenta += subtotal;
            }

            // Crear y guardar la compra
            compraControlador.crearCompra(idEmpleado, fechaVenta, totalVenta, detalles);

            limpiar();

            // Recargar la tabla de compras
            cargarDatosTablaCompras();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al guardar la compra: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_accionBotonGuardar

    private void accionBotonActualizar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accionBotonActualizar
        // TODO add your handling code here:
        try {
            // Obtener el índice de la fila seleccionada en tablaVentas
            int filaSeleccionada = tablaCompras.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione una compra para actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener el idVenta de la fila seleccionada
            DefaultTableModel modelVentas = (DefaultTableModel) tablaCompras.getModel();
            int idCompra = (int) modelVentas.getValueAt(filaSeleccionada, 0);

            // Obtener el índice seleccionado de empleados
            int indiceEmpleado = comboEmpleados.getSelectedIndex();
            if (indiceEmpleado < 0) {
                JOptionPane.showMessageDialog(this, "Seleccione un empleado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener la lista de empleados
            List<Empleado> empleados = empleadoControlador.obtenerTodosEmpleados();
            int idEmpleado = empleados.get(indiceEmpleado).getIdEmpleado();

            // Obtener la fecha seleccionada
            Date fechaVenta = selectorfechaCompra.getDate();
            if (fechaVenta == null) {
                JOptionPane.showMessageDialog(this, "Seleccione una fecha.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener los detalles de la tabla tablaDetalles
            DefaultTableModel modelDetalles = (DefaultTableModel) tablaDetalles.getModel();
            int rowCount = modelDetalles.getRowCount();
            if (rowCount == 0) {
                JOptionPane.showMessageDialog(this, "Agregue al menos un producto a la compra.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Calcular el total de la compra
            float totalCompra = 0;
            for (int i = 0; i < rowCount; i++) {
                totalCompra += ((Number) modelDetalles.getValueAt(i, 4)).floatValue(); // Suma los subtotales
            }

            // Actualizar la compra principal
            compraControlador.actualizarCompra(idCompra, idEmpleado, fechaVenta, totalCompra);

            // Eliminar los detalles antiguos de la compra
            List<DetalleCompra> detallesAntiguos = detalleCompraControlador.obtenerTodosDetallesCompra();
            if (detallesAntiguos != null) {
                for (DetalleCompra detalle : detallesAntiguos) {
                    if (detalle.getIdCompra() == idCompra) {
                        detalleCompraControlador.eliminarDetalleCompra(detalle.getIdDetalleCompra());
                    }
                }
            }

            // Insertar los nuevos detalles
            for (int i = 0; i < rowCount; i++) {
                int idProducto = (int) modelDetalles.getValueAt(i, 0);
                float precioUnitario = ((Number) modelDetalles.getValueAt(i, 2)).floatValue();
                int cantidad = (int) modelDetalles.getValueAt(i, 3);

                // Crear y guardar el nuevo detalle
                DetalleCompra detalle = new DetalleCompra();
                detalle.setIdCompra(idCompra);
                detalle.setIdProducto(idProducto);
                detalle.setCantidad(cantidad);
                detalle.setPrecioUnitario(precioUnitario);
                detalleCompraControlador.crearDetalleCompra(idCompra, idProducto, cantidad, precioUnitario);
            }

            // Limpiar la tabla de detalles y el formulario
            tablaDetalles.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"ID Producto", "Producto", "Precio Unitario", "Cantidad", "Subtotal"}));
            limpiar();

            // Recargar la tabla de compras
            cargarDatosTablaCompras();

            // Habilitar botones nuevamente
            btnEliminar.setEnabled(true);
            btnGuardar.setEnabled(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar la compra: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_accionBotonActualizar

    private void accionBotonLimpiar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accionBotonLimpiar
        // TODO add your handling code here:
        limpiar();
    }//GEN-LAST:event_accionBotonLimpiar

    private void textBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textBuscarKeyTyped
        // TODO add your handling code here:
        String textoBusqueda = textBuscar.getText().trim().toLowerCase();
        List<Compra> compras = compraControlador.obtenerTodasCompras();

        DefaultTableModel modelo = (DefaultTableModel) tablaCompras.getModel();
        modelo.setRowCount(0);

        if (compras != null) {
            for (Compra c : compras) {
                Empleado empleado = empleadoControlador.obtenerEmpleadoPorId(c.getIdEmpleado());
                String nombreEmpleado = empleado.getPrimerNombre() + " " + empleado.getPrimerApellido();

                if (textoBusqueda.isEmpty()
                        || nombreEmpleado.toLowerCase().contains(textoBusqueda)) {
                    Object[] fila = {
                        c.getIdCompra(),
                        c.getFechaCompra(),
                        nombreEmpleado,
                        c.getTotalCompra()
                    };
                    modelo.addRow(fila);
                }
            }
        }
    }//GEN-LAST:event_textBuscarKeyTyped

    private void tablaComprasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaComprasMouseClicked
        // TODO add your handling code here:
         if (evt.getClickCount() == 2) {
            try {
                
                btnEliminar.setEnabled(false);
                btnGuardar.setEnabled(false);
                
                // Obtener el índice de la fila seleccionada en tablaCompras
                int filaSeleccionada = tablaCompras.getSelectedRow();
                if (filaSeleccionada == -1) {
                    return; // No hacer nada si no hay fila seleccionada
                }

                // Obtener el idVenta de la fila seleccionada
                DefaultTableModel modelVentas = (DefaultTableModel) tablaCompras.getModel();
                int idCompra = (int) modelVentas.getValueAt(filaSeleccionada, 0);

                // Obtener la venta seleccionada para acceder a sus datos
                List<Compra> compras = compraControlador.obtenerTodasCompras();
                Compra compraSeleccionada = null;
                for (Compra c : compras) {
                    if (c.getIdCompra() == idCompra) {
                        compraSeleccionada = c;
                        break;
                    }
                }
                if (compraSeleccionada == null) {
                    JOptionPane.showMessageDialog(this, "Compra no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Cargar empleado en comboEmpleados
                List<Empleado> empleados = empleadoControlador.obtenerTodosEmpleados();
                int indiceEmpleado = -1;
                for (int i = 0; i < empleados.size(); i++) {
                    if (empleados.get(i).getIdEmpleado() == compraSeleccionada.getIdEmpleado()) {
                        indiceEmpleado = i;
                        break;
                    }
                }
                if (indiceEmpleado != -1) {
                    idEmpleadoSeleccionado = compraSeleccionada.getIdEmpleado();
                    comboEmpleados.setSelectedIndex(indiceEmpleado);
                } else {
                    JOptionPane.showMessageDialog(this, "Empleado no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Detener el timer actual
                if (timer != null) {
                    timer.stop();
                }
                
                // Asignar la hora al label
                horabd = true;
                java.text.SimpleDateFormat horaFormato = new java.text.SimpleDateFormat("HH:mm:ss");
                String horaCompra = horaFormato.format(compraSeleccionada.getFechaCompra());
                hora.setText(horaCompra); // Ajusta 'horaLabel' al nombre real de tu JLabel

                // Cargar la fecha en selectorfechaContratacion
                selectorfechaCompra.setDate(compraSeleccionada.getFechaCompra());

                // Limpiar y cargar los detalles en tablaDetalles
                DefaultTableModel modelDetalles = (DefaultTableModel) tablaDetalles.getModel();
                modelDetalles.setRowCount(0);

                List<DetalleCompra> detalles = detalleCompraControlador.obtenerTodosDetallesCompra();
                if (detalles != null) {
                    for (DetalleCompra detalle : detalles) {
                        if (detalle.getIdCompra() == idCompra) {
                            Producto producto = productoControlador.obtenerProductoPorId(detalle.getIdProducto());
                            String nombreProducto = (producto != null) ? producto.getNombreProducto() : "Desconocido";

                            Object[] row = {
                                detalle.getIdProducto(),
                                nombreProducto,
                                detalle.getPrecioUnitario(),
                                detalle.getCantidad(),
                                detalle.getPrecioUnitario() * detalle.getCantidad() // Subtotal
                            };
                            modelDetalles.addRow(row);
                        }
                    }
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al cargar los datos de la compra: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_tablaComprasMouseClicked

    private void accionBotonQuitarDetalle(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accionBotonQuitarDetalle
        // TODO add your handling code here:
        try {
            // Obtener el índice de la fila seleccionada en tablaDetalles
            int filaSeleccionada = tablaDetalles.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un detalle para quitar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Eliminar la fila seleccionada del modelo de la tabla
            DefaultTableModel model = (DefaultTableModel) tablaDetalles.getModel();
            model.removeRow(filaSeleccionada);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al quitar el detalle: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_accionBotonQuitarDetalle


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnQuitarDetalle;
    private javax.swing.JComboBox<String> comboEmpleados;
    private javax.swing.JComboBox<String> comboProductos;
    private javax.swing.JLabel hora;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private com.toedter.calendar.JDateChooser selectorfechaCompra;
    private javax.swing.JTable tablaCompras;
    private javax.swing.JTable tablaDetalles;
    private javax.swing.JTextField textBuscar;
    private javax.swing.JTextField textCantidad;
    private javax.swing.JTextField textPrecio;
    // End of variables declaration//GEN-END:variables
}
