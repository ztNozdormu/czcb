package cn.com.czcb.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.com.czcb.pub.InitConfig;

/**
 * 说明 附件工具类
 * 
 * @author Cron
 * @date 2018年4月16日 下午4:53:32
 */
public class FileUtil {

	/**
	 * 通过url保存图片至服务器
	 * 
	 * @param urlString
	 * @param request
	 * @return 保存后的路径
	 */
	public static String saveImg(String urlString, HttpServletRequest request) {

		String savePath = request.getSession().getServletContext().getRealPath("/HJX/images/feedbackImg");// 保存的服务器地址
		String suffix = urlString.substring(urlString.lastIndexOf(".") + 1);
		String filename = UUID.randomUUID().toString().replace("-", "") + "." + suffix;// 文件名称
		try {
			// 构造URL
			URL url = new URL(urlString);
			// 打开连接
			URLConnection con = url.openConnection();
			// 设置请求超时为5s
			con.setConnectTimeout(5 * 1000);
			// 输入流
			InputStream is = con.getInputStream();

			File sf = new File(savePath);
			if (!sf.exists()) {
				sf.mkdirs();
			}
			OutputStream os = new FileOutputStream(sf.getPath() + "\\" + filename);
			// 1K的数据缓冲
			byte[] bs = new byte[1024];
			// 读取到的数据长度
			int len;
			// 开始读取
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);
			}
			// 完毕，关闭所有链接
			os.close();
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return savePath + "/" + filename;
	}

	/**
	 * wx小程序保存图片
	 * 
	 * @param request
	 * @return 保存后的路径
	 */
	public static String saveImg(HttpServletRequest request) {

		// 保存到服务器的位置
		String savePath = request.getSession().getServletContext().getRealPath("/HJX/images/feedbackImg");
		String oldFileName = request.getParameter("url").toString();
		// 后綴
		String suffix = oldFileName.substring(oldFileName.lastIndexOf(".") + 1);
		String fileName = UUIDUtil.uuid() + "." + suffix;

		try {
			File dir = new File(savePath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			request.setCharacterEncoding("utf-8"); // 设置编码
			// 获得磁盘文件条目工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();

			// 如果没以下两行设置的话,上传大的文件会占用很多内存，
			// 设置暂时存放的存储室,这个存储室可以和最终存储文件的目录不同
			/**
			 * 原理: 它是先存到暂时存储室，然后再真正写到对应目录的硬盘上， 按理来说当上传一个文件时，其实是上传了两份，第一个是以 .tem 格式的
			 * 然后再将其真正写到对应目录的硬盘上
			 */
			factory.setRepository(dir);
			// 设置缓存的大小，当上传文件的容量超过该缓存时，直接放到暂时存储室
			factory.setSizeThreshold(1024 * 1024);
			// 高水平的API文件上传处理
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> list = upload.parseRequest(request);
			FileItem picture = null;
			for (FileItem item : list) {
				// 获取表单的属性名字
				String name = item.getFieldName();
				// 如果获取的表单信息是普通的 文本 信息
				if (item.isFormField()) {
					// 获取用户具体输入的字符串
					String value = item.getString();
					request.setAttribute(name, value);
				} else {
					picture = item;
				}
			}

			String destPath = savePath + fileName;
			// 保存
			File file = new File(destPath);
			OutputStream out = new FileOutputStream(file);
			InputStream in = picture.getInputStream();
			int length = 0;
			byte[] buf = new byte[1024];
			// in.read(buf) 每次读到的数据存放在buf 数组中
			while ((length = in.read(buf)) != -1) {
				// 在buf数组中取出数据写到（输出流）磁盘上
				out.write(buf, 0, length);
			}
			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return savePath.substring(savePath.lastIndexOf("heijinxin-tft")) + "/" + fileName;
	}

	/**
	 * wx小程序保存临时文件
	 * 
	 * @param request
	 * @return 保存位置
	 * @throws IOException
	 */
	public static String saveImgByCache(HttpServletRequest request) throws IOException {
		// 保存服务器路径
		String savePath = request.getSession().getServletContext().getRealPath("/");
		savePath = savePath.substring(0, savePath.indexOf("webapps") - 1)+"/webapps/HJXAccessory/images/feedbackImg";
		// 临时文件路径
		String cacheDir = savePath.substring(0, savePath.indexOf("webapps") - 1) + InitConfig.getTmpFiledir();

		File dir = new File(savePath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String oldFileName = request.getParameter("url").toString();
		// 后綴
		String suffix = oldFileName.substring(oldFileName.lastIndexOf(".") + 1);

		// 文件拷贝
		List<File> fileList = new ArrayList<>();
		getFileList(new File(cacheDir), ".tmp", fileList);
		String name = UUIDUtil.uuid();
		copyFile(fileList.get(0), new File(savePath + "/" + name + ".tmp"));
		// write2File(fileList, new File(savePath+"\\"+ UUIDUtil.uuid()+".tmp"));

		// 文件重命名
		suffixRename(savePath, "tmp", suffix);

		// 删除临时缓存文件
		fileList = new ArrayList<>();
		getFileList(new File(cacheDir), ".tmp", fileList);
		if (fileList.size() > 0) {
			for (File file : fileList) {
				if (file.exists() && file.isFile()) {
					file.delete();
				}
			}
		}

//		return savePath.substring(savePath.lastIndexOf("heijinxin-tft")) + "/" + name + "." + suffix;// 目前是单次单个上传，单次多个则修改返回
		return savePath.substring(savePath.lastIndexOf("HJXAccessory")) + "/" + name + "." + suffix;// 目前是单次单个上传，单次多个则修改返回
	}

	/**
	 * 复制文件
	 * 
	 * @param fromFile
	 * @param toFile
	 * @throws IOException
	 */
	public static void copyFile(File fromFile, File toFile) throws IOException {

		FileInputStream ins = new FileInputStream(fromFile);
		FileOutputStream out = new FileOutputStream(toFile);
		byte[] b = new byte[1024];
		int n = 0;
		while ((n = ins.read(b)) != -1) {
			out.write(b, 0, n);
		}

		ins.close();
		out.close();
	}

	/**
	 * 将容器中的文件遍历，写入到目的文件中
	 * 
	 * @param list
	 *            存放满足条件的文件的集合
	 * @param desFile
	 *            要写入的目的文件
	 */
	public static void write2File(List<File> fileList, File desFile) {
		BufferedWriter bw = null;
		try {
			if (!desFile.exists()) {
				// 创建文件
				desFile.createNewFile();
			}
			// 使用字符流写入到目的文件中
			bw = new BufferedWriter(new FileWriter(desFile));
			// 遍历List集合
			for (File file : fileList) {
				bw.write(file.getAbsolutePath());// 写入目的文件绝对路径
				bw.newLine();
				bw.flush();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null) {
					bw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @param dir
	 *            需要遍历的目录
	 * @param filter
	 *            过滤满足条件的文件
	 * @param fileList
	 *            存放符合条件的容器
	 */
	public static void getFileList(File dir, String filter, List<File> fileList) {
		if (dir.exists()) {
			File[] files = dir.listFiles();// 找到目录下面的所有文件
			if (files.length > 0) {
				for (File file : files) {
					// 递归
					if (file.isDirectory()) {
						// getFileList(file, filter, fileList);
					} else {
						// 对遍历的文件进行过滤，符合条件的放入List集合中
						if (file.getName().contains(filter)) {
							fileList.add(file);
						}
					}
				}
			}
		}
	}

	/**
	 * q	
	 * @param path
	 *            要批量修改后缀名文件夹路径
	 * @param from
	 *            源文件后缀名
	 * @param to
	 *            修改后目标文件后缀名
	 */
	public static void suffixRename(String path, String from, String to) {
		File file = new File(path);
		File[] fs = file.listFiles();
		for (int i = 0; i < fs.length; i++) {
			File f2 = fs[i];
			if (f2.isDirectory()) {
				suffixRename(f2.getPath(), from, to);
			} else {
				String name = f2.getName();
				if (name.endsWith(from)) {
					f2.renameTo(new File(f2.getParent() + File.separator + name.substring(0, name.indexOf(from)) + to));
				}
			}
		}
	}

	/**
	 * 保存文件流
	 * 
	 * @param response
	 * @param request
	 * @return
	 * @throws IOException
	 */
	// public String addCircle(HttpServletResponse response, HttpServletRequest
	// request) throws IOException {
	// request.setCharacterEncoding("utf-8");
	// response.setContentType("text/html;charset=utf-8");
	//
	// InputStream inputStream = null;
	// FileOutputStream outputStream = null;
	// try {
	// inputStream = request.getInputStream();
	// // 给新文件拼上时间毫秒，防止重名
	// long now = System.currentTimeMillis();
	// File file = new File("c:/", "file-" + now + ".txt");
	// file.createNewFile();
	//
	// outputStream = new FileOutputStream(file);
	//
	// byte temp[] = new byte[1024];
	// int size = -1;
	// while ((size = inputStream.read(temp)) != -1) { // 每次读取1KB，直至读完
	// outputStream.write(temp, 0, size);
	// }
	// } catch (IOException e) {
	//
	// } finally {
	// outputStream.close();
	// inputStream.close();
	// }
	//
	// return null;
	// }
}
