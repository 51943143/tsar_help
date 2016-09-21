package com.tsar.client;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.json.Json;
import org.nutz.lang.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class ReadFileNLine {

	@Value("${tsar.file_path}")
	private String file_path;

	@Value("${tsar.file_read_lines}")
	private long file_read_lines = 100;

	@RequestMapping(value = "/getTsarDatas", method = RequestMethod.GET)
	public String getTsarDatas() {
		return Json.toJson(getLastNLine());
	}

	private List<String> readLastNLine() {
		return ReadFileNLine.readLastNLine(new File(this.file_path), this.file_read_lines);
	}

	public List<ServerMetric> getLastNLine() {
		List<String> slist = this.readLastNLine();
		List<ServerMetric> list = new ArrayList<ServerMetric>();
		for (String s : slist) {
			ServerMetric m = new ServerMetric();
			String[] str =filterNull(s.split(" "));
			m.setTime(str[0].trim());
			m.setCpu(str[1].trim());
			m.setMem(str[2].trim());
			m.setTcp_retran(str[3].trim());
			m.setTraffic_in(str[4].trim());
			m.setTraffic_out(str[5].trim());
			m.setIo_sda(str[6].trim());
			m.setLoad_rate(str[7].trim());
			list.add(m);
		}
		return list;

	}
	
	private String[]  filterNull(String[] str) {
//		System.out.println(Json.toJson(str));
		String[] list = new String[8];
		int j=0;
		for(int i=0;i<str.length;i++) {
			if(!Strings.isEmpty(str[i].trim())){
				list[j]=str[i].trim();j++;
			}
		}
		return list;
		
	}

	/**
	 * 读取文件最后N行
	 * 
	 * 根据换行符判断当前的行数， 使用统计来判断当前读取第N行
	 * 
	 * PS:输出的List是倒叙，需要对List反转输出
	 * 
	 * @param file
	 *            待文件
	 * @param numRead
	 *            读取的行数
	 * @return List<String>
	 */
	public static List<String> readLastNLine(File file, long numRead) {
		// 定义结果集
		List<String> result = new ArrayList<String>();
		// 行数统计
		long count = 0;
		// 排除不可读状态
		if (!file.exists() || file.isDirectory() || !file.canRead()) {
			return null;
		}
		// 使用随机读取
		RandomAccessFile fileRead = null;
		try {
			// 使用读模式
			fileRead = new RandomAccessFile(file, "r");
			// 读取文件长度
			long length = fileRead.length();
			// 如果是0，代表是空文件，直接返回空结果
			if (length == 0L) {
				return result;
			} else {
				// 初始化游标
				long pos = length - 1;
				while (pos > 0) {
					pos--;
					// 开始读取
					fileRead.seek(pos);
					// 如果读取到\n代表是读取到一行
					if (fileRead.readByte() == '\n') {
						// 使用readLine获取当前行
						String line = fileRead.readLine();
						// 保存结果
						result.add(line);

						// 打印当前行
						// System.out.println(line);

						// 行数统计，如果到达了numRead指定的行数，就跳出循环
						count++;
						if (count == numRead) {
							break;
						}
					}
				}
				if (pos == 0) {
					fileRead.seek(0);
					result.add(fileRead.readLine());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileRead != null) {
				try {
					// 关闭资源
					fileRead.close();
				} catch (Exception e) {
				}
			}
		}
		return reverseAndFilter(result);
	}

	private static List<String> reverseAndFilter(List<String> list) {
		List<String> rlist = new ArrayList<String>();
		for (int i = list.size(); i > 0; i--) {
			String v = list.get(i - 1);
			if (!valid(v)) {
				rlist.add(v);
			}
		}
		return rlist;
	}

	private static boolean valid(String s) {
		String[] slist = s.split("   ");
		if (slist[0].indexOf("Time") >= 0)
			return true;
		return false;

	}
}
