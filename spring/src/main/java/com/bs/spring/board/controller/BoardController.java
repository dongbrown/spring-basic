package com.bs.spring.board.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.bs.spring.board.model.dto.Attachment;
import com.bs.spring.board.model.dto.Board;
import com.bs.spring.board.model.service.BoardService;
import com.bs.spring.common.PageFactory;
import com.bs.spring.common.exception.BadAuthenticationException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/board")
@RequiredArgsConstructor
@SessionAttributes({ "loginMember" })
public class BoardController {

	private final BoardService service;
	private final PageFactory pageFactory;

	@GetMapping("/boardlist.do")
	public void boardList(@RequestParam(defaultValue = "1") int cPage, @RequestParam(defaultValue = "5") int numPerpage,
			Model m) {
		List<Board> result = service.selectBoardList(Map.of("cPage", cPage, "numPerpage", numPerpage));
		m.addAttribute("boards", result);
		int totalContents = service.selectBoardCount();
		System.out.println(result);
		String pageBar = pageFactory.getPage(cPage, numPerpage, totalContents, "boardlist.do");
		m.addAttribute("totalContents", totalContents);
		m.addAttribute("pageBar", pageBar);
	}

	@PostMapping("/insertboard.do")
	public String insertBoard(Board b, MultipartFile[] upFile, Model model, HttpSession session) {
		// inputBoard.jsp 파일 등록 input태그 name = upFile => MultipartFile 변수명 맞춰줘야함
//		log.debug("{}", upFile.getName()); //파일 확인
//		log.debug("{}", upFile.getOriginalFilename());
//		log.debug("{}", upFile.getSize());
		List<Attachment> files = new ArrayList<>();
		// 파일 저장할 경로를 가져오기
		String path = session.getServletContext().getRealPath("/resources/upload/board");
		if (upFile != null) {
			for (MultipartFile file : upFile) {
				if (!file.isEmpty()) {

					// 파일 rename 설정
//					String oriName = upFile.getOriginalFilename();
					String oriName = file.getOriginalFilename();
					String ext = oriName.substring(oriName.lastIndexOf("."));
					// 마지막 .에서 잘라서 확장자 가져오기
					Date today = new Date(System.currentTimeMillis()); // util.Date
					int randomVal = (int) (Math.random() * 10000) + 1;
					String rename = "DH_" + (new SimpleDateFormat("yyyyMMdd_HHmmssSSS").format(today)) + "_" + randomVal
							+ ext;
					File dir = new File(path);
					if (!dir.exists())
						dir.mkdirs();

					try {
						// 파일을 저장
//						upFile.transferTo(new File(path, rename));
						file.transferTo(new File(path, rename));
						files.add(Attachment.builder().originalFilename(oriName).renamedFilename(rename).build());
					} catch (IOException e) {
						e.printStackTrace();
					}
					// 업로드 실패 시 runtimeException 발생 시키기 -> runtimeException핸들러 -> 에러페이지 이동
				}
			}
			// 반복문 끝나고 files board 대입
			b.setFiles(files);
		}

		int result = 0;
		String msg, loc;
		try {
			result = service.insertBoard(b);
			msg = "게시글 등록 성공!";
			loc = "/board/boardlist.do";
		} catch (BadAuthenticationException e) {
			msg = "게시글 등록 실패 다시 시도하세요!";
			loc = "/board/inputboard.do";
			// 게시글 등록 실패 시 -> 파일 지우기
			files.stream().forEach(f -> {
				File delFile = new File(path + "/" + f);
				delFile.delete();
			});

		}
		model.addAttribute("msg", msg);
		model.addAttribute("loc", loc);

		return "common/msg";
	}

//	@GetMapping("/inputboard.do")
//	public void inputboard() {
//	}
	// 단순 페이지 이동 (화면 전환)

	@GetMapping("/boardDetail")
	public void boardView(int boardNo, Model model) {
		service.selectBoardCount();
		Board b = service.selectBoardByNo(boardNo);
		model.addAttribute("board", b);
	}

	@DeleteMapping("/deleteBoard.do")
	public String deleteBoard(@RequestParam int boardNo) {
		int result = service.deleteBoard(boardNo);
		if (result > 0) {
			log.debug("삭제 완료");
		} else {
			log.debug("삭제 실패");
		}
		return "redirect:/board/boardlist.do";
	}

	@GetMapping("/filedownload.do")
	public void filedownload(String oriname, String rename, 
			OutputStream out, HttpServletResponse response,
			HttpSession session) {
		String path = session.getServletContext().getRealPath("/resources/upload/board/");
		File downloadFile = new File(path + rename);
		try (FileInputStream fis = new FileInputStream(downloadFile);
				BufferedInputStream bis = new BufferedInputStream(fis);
				BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream())) {

			String encoding = new String(oriname.getBytes("UTF-8"), "ISO-8859-1");

			response.setContentType("application/octet-stream;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename=\"" + encoding + "\"");
			int data = 1;
			while ((data = bis.read()) != -1) {
				bos.write(data);
			}

		} catch (IOException e) {

		}
	}



}
