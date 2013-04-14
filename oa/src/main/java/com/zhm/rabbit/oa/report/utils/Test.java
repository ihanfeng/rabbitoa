package com.zhm.rabbit.oa.report.utils;

import javax.swing.JTable;

public class Test {
	public static void main(String[] args) {
//		Object[][] cellData = {{"庄海明", "286600136@qq.com"},{"范婷", "582052093@qq.com"}};
//		String[] columnNames = {"姓名","邮箱"};
		Object[][] cellData = {{"庄海明", "286600136@qq.com"},{"范婷", "582052093@qq.com"}};
		String[] columnNames = {"姓名","邮箱"};
		int[] columnWidths = {0,200};
		JTable tb = new JTable(cellData, columnNames);
		JDesign.Preview("测试中文", tb,columnWidths);
	}
}
