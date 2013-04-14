package com.zhm.rabbit.oa.report.utils;

import java.util.Enumeration;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignFrame;
import net.sf.jasperreports.engine.design.JRDesignLine;
import net.sf.jasperreports.engine.design.JRDesignSection;
import net.sf.jasperreports.engine.design.JRDesignStaticText;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.type.HorizontalAlignEnum;
import net.sf.jasperreports.engine.type.VerticalAlignEnum;

import com.zhm.rabbit.oa.utils.PinYinToolkit;

/**
 * @author zhmlvft
 * 
 */
public class JDesign {
	static JRDesignBand tIband = null;
	static JRDesignBand cHband = null;
	static JRDesignBand dEband = null;
	static JRDesignStaticText staticText = null;
	static JRDesignFrame frame = null;
	static JRDesignLine line = null;
	static JRDesignTextField textField = null;
	static JRDesignExpression expression = null;
	static int jDWidth = 595; // default width
	static int jDHeight = 842; // default height
	static int cWidth = 555; // default columnWidth
	static JRDesignField field;
	static int width = 0;
	static DefaultTableModel dtm = new DefaultTableModel();
	static JasperDesign jasperDesign = null;
	static JasperReport jasperReport = null;
	static JasperPrint jasperPrint = null;

	public static JasperPrint Preview(String rTitle, JTable tb, int[] columnWidths) {
		String colName[] = new String[tb.getColumnCount()];
		for (int i = 0; i < tb.getColumnCount(); i++) {
			colName[i] = PinYinToolkit.cn2Spell(tb.getColumnName(i));
		}
		String data[][] = new String[tb.getRowCount()][tb.getColumnCount()];
		for (int i = 0; i < tb.getRowCount(); i++) {
			for (int j = 0; j < tb.getColumnCount(); j++) {
				data[i][j] = String.valueOf(tb.getValueAt(i, j));
			}
		}
		dtm.setDataVector(data, colName);
		try {
			jasperDesign = JDesign.getJasperDesign(rTitle, tb,columnWidths);
		} catch (JRException e) {
			System.out.println("获取生成报表模板信息失败");
			e.printStackTrace();
		}
		try {
			jasperReport = JasperCompileManager.compileReport(jasperDesign);
		} catch (JRException e) {
			System.out.println("生成报表模板失败");
			e.printStackTrace();
		}
		try {
			jasperPrint = JasperFillManager.fillReport(jasperReport, null,
					new JRTableModelDataSource(dtm));
		} catch (JRException e) {
			System.out.println("填充报表模板数据失败");
			e.printStackTrace();
		}
		return jasperPrint;
	}

	public static JasperDesign getJasperDesign(String rTitle, JTable tb, int[] columnWidths)
			throws JRException {
		JDesign.FitTableColumns(tb);// 自动调整列宽
		if (width > 555) {
			jDWidth = 842;
			jDHeight = 595;
			cWidth = 802;
		} else {
			width = 555;
		}

		// JasperDesign
		JasperDesign jasperDesign = new JasperDesign();
		jasperDesign.setName(System.currentTimeMillis()+"aaa");
		jasperDesign.setPageWidth(jDWidth);
		jasperDesign.setPageHeight(jDHeight);
		jasperDesign.setColumnWidth(cWidth);
		jasperDesign.setColumnSpacing(0);
		jasperDesign.setLeftMargin(20);
		jasperDesign.setRightMargin(20);
		jasperDesign.setTopMargin(20);
		jasperDesign.setBottomMargin(20);

		// Title
		tIband = new JRDesignBand();
		tIband.setHeight(50);
		staticText = new JRDesignStaticText();
		staticText.setX(0);
		staticText.setY(0);
		staticText.setWidth(cWidth);
		staticText.setHeight(50);
		staticText.setHorizontalAlignment(HorizontalAlignEnum.CENTER);
		staticText.setText(rTitle);
		staticText.setFontSize(22);
		staticText.setPdfFontName("STSong-Light");
		staticText.setPdfEmbedded(true);
		staticText.setPdfEncoding("UniGB-UCS2-H");
		staticText.setBold(true);
		tIband.addElement(staticText);
		jasperDesign.setTitle(tIband);

		cHband = new JRDesignBand();
		cHband.setHeight(20);
		frame = new JRDesignFrame();
		frame.setX(0);
		frame.setY(0);
		frame.setWidth(cWidth);
		frame.setHeight(20);
		cHband.addElement(frame);

		dEband = new JRDesignBand();
		dEband.setHeight(20);

		for (int i = 0; i < tb.getColumnCount(); i++) {

			field = new JRDesignField();
			field.setName(PinYinToolkit.cn2Spell(tb.getColumnName(i)));
			field.setValueClass(java.lang.String.class);
			jasperDesign.addField(field);

			staticText = new JRDesignStaticText();
			staticText.setX(columnWidths[i]);
			staticText.setPdfFontName("STSong-Light");
			staticText.setPdfEmbedded(true);
			staticText.setPdfEncoding("UniGB-UCS2-H");
			staticText.setY(0);
			staticText.setWidth(tb.getColumnModel().getColumn(i).getWidth());
			staticText.setHeight(15);
			staticText.setText(tb.getColumnName(i));
			staticText.setBold(true);
			frame.addElement(staticText);

			textField = new JRDesignTextField();
			textField.setX(columnWidths[i]);
			textField.setVerticalAlignment(VerticalAlignEnum.MIDDLE);
			textField.setY(0);
			textField.setWidth(tb.getColumnModel().getColumn(i).getWidth());
			textField.setHeight(15);
			textField.setPdfFontName("STSong-Light");
			textField.setPdfEmbedded(true);
			textField.setPdfEncoding("UniGB-UCS2-H");
			expression = new JRDesignExpression();
			expression.setValueClass(java.lang.String.class);
			//这里改下
			expression.setText("$F{" + PinYinToolkit.cn2Spell(tb.getColumnName(i)) + "}");
			textField.setExpression(expression);
			dEband.addElement(textField);
		}
		line = new JRDesignLine();
		line.setX(0);
		line.setY(19);
		line.setWidth(cWidth);
		line.setHeight(0);
		frame.addElement(line);

		jasperDesign.setColumnHeader(cHband);

		line = new JRDesignLine();
		line.setX(0);
		line.setY(19);
		line.setWidth(cWidth);
		line.setHeight(0);
		dEband.addElement(line);
		((JRDesignSection)jasperDesign.getDetailSection()).addBand(dEband);
		return jasperDesign;

	}

	public static void FitTableColumns(JTable myTable) 
	{
		// (此方法主要用来动态调整单元格列宽)
		JTableHeader header = myTable.getTableHeader();// 获取表头
		int rowCount = myTable.getRowCount();// 获取行数
		Enumeration columns = myTable.getColumnModel().getColumns();// 获取所有列
		while (columns.hasMoreElements()) {
			TableColumn column = (TableColumn) columns.nextElement();
			int col = header.getColumnModel().getColumnIndex(
					column.getIdentifier());
			int width = (int) myTable
					.getTableHeader()
					.getDefaultRenderer()
					.getTableCellRendererComponent(myTable,
							column.getIdentifier(), false, false, -1, col)
					.getPreferredSize().getWidth();
			for (int row = 0; row < rowCount; row++) {
				int preferedWidth = (int) myTable
						.getCellRenderer(row, col)
						.getTableCellRendererComponent(myTable,
								myTable.getValueAt(row, col), false, false,
								row, col).getPreferredSize().getWidth();
				width = Math.max(width, preferedWidth);
			}
			header.setResizingColumn(column);
			column.setWidth(width + myTable.getIntercellSpacing().width);
			if (width + myTable.getIntercellSpacing().width < 50) {
				column.setWidth(50);
			}
		}
	}

}