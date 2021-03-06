package com.kh.sogon.board.model.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kh.sogon.board.model.dao.BoardDAO;
import com.kh.sogon.board.model.vo.Attachment;
import com.kh.sogon.board.model.vo.Board;
import com.kh.sogon.board.model.vo.PageInfo;
import com.kh.sogon.board.model.vo.Search;


@Service
public class BoardServiceImpl implements BoardService{

	@Autowired
	private BoardDAO boardDAO;

	@Autowired// 의존성 주입 (DI)
	private PageInfo pInfo;
	
	
	// 페이징 처리를 위한 Service 구현
	@Override
	public PageInfo pagination(int cp) {
	
		
		// 1) 전체 게시글 수 조회
		int listCount = boardDAO.getListCount();
		
		// 2) setPageInfo
		pInfo.setPageInfo(cp, listCount);
		
		
		
		
		return pInfo;
	}
	

	

	//게시글 목록 조회 Service 구현
	@Override
	public List<Board> selectList(PageInfo pInfo) {
		
		
		
		return boardDAO.selectList(pInfo);
	}

	
	// 게시글 상세조회 Service 구현
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Board selectBoard(int qnaNo) {
		
		// 게시글 조회
		Board board = boardDAO.selectBoard(qnaNo);
		
		
	
		
		
		
		return board;
	}




	// 게시글 등록 Service 구현
		@Transactional(rollbackFor = Exception.class)
		@Override
		public int insertBoard(Board board, List<MultipartFile> images, String savePath) {
			
			int result = 0;
			
			
			// 1) 다음 SEQ_MNO를 얻어옴.
			int qnaNo = boardDAO.selectNextNo();
			
			
			if (qnaNo > 0) { // 다음 번호를 정상적으로 얻어 왔을 때
				// 다음 번호 board 객체에 세팅
				board.setQnaNo(qnaNo);
				
				//board.setQnaContent(replaceParameter(board.getQnaContent()));
				
				// 2) 게시글(board) DB 삽입
				result = boardDAO.insertBoard(board);
				
		
			
				// 3) 파일정보 DB 저장
			List<Attachment> files = new ArrayList<Attachment>();
			Attachment at = null;
			String filePath = "/resources/uploadImages";
			
			for(int i=0; i<images.size(); i++) {
				
				if(!images.get(i).getOriginalFilename().equals("")) {
					// 업로드된 이미지가 있다면
					
					
					// 파일명 변경 (cos.jar는 별도의 RenamePolicy가 존재하지만 스프링은 없음.)
					// -> 별도의 메소드를 제작하여 변경하기
					String changeName = rename(images.get(i).getOriginalFilename());
					
					// Attachment 객체 생성
					at = new Attachment(qnaNo, 
							images.get(i).
							getOriginalFilename(), 
							changeName, 
							filePath, 
							i);
					
					
					result = boardDAO.insertAttachment(at);
			
					
				}
				files.add(at);
				
				
			}
			
			
				// 4) 파일을 서버에 저장
				if(result > 0 ) {
					for(int i = 0; i<images.size(); i++) {
						
						if(!images.get(i).getOriginalFilename().equals("")) {
							// 이미지가 업로드가 되었다면
							
							
							// transferTo(경로) : 지정한 경로에 업로드된 바이트 상태의 파일을 실제 파일로 변환해서 저장해라.
							try {
								images.get(i).transferTo(new File(savePath + "/" + files.get(i).getFileChangeName()));
							
								
							} catch (Exception e) {
								
								e.printStackTrace();
								
								// 서버에 파일 저장 중 문제가 발생할 경우
								// 이미 DB에 삽입되어 있는 파일 정보를 삭제하는 DAO를 호출.
								boardDAO.deleteAttachment(qnaNo);
								
							}
							
						}
					}
				}
			
			}
			
			
			
			return result;
		}
		




		private String rename(String originalFilename) {
			 SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
		        String date = sdf.format(new java.util.Date(System.currentTimeMillis()));

		        int ranNum = (int)(Math.random()*100000); // 5자리 랜덤 숫자 생성

		        String str = "" + String.format("%05d", ranNum);
		        //String.format : 문자열을 지정된 패턴의 형식으로 변경하는 메소드
		        // %05d : 오른쪽 정렬된 십진 정수(d) 5자리(5)형태로 변경. 빈자리는 0으로 채움(0)

		        String ext = originalFilename.substring(originalFilename.lastIndexOf("."));

		        return date + "" + str + ext;
		}




		private String replaceParameter(String param) {
	        String result = param;
	        if(param != null) {
	            result = result.replaceAll("&", "&amp;");
	            result = result.replaceAll("<", "&lt;");
	            result = result.replaceAll(">", "&gt;");
	            result = result.replaceAll("\"", "&quot;");
	        }

	        return result;
	    }

		



	@Override
	@Transactional(rollbackFor = Exception.class)
	public int deleteBoard(int qnaNo) {
		int result = 0;
		
		result = boardDAO.deleteBoard(qnaNo);
		
		return result;
	}


	// 게시글 수정 구현 Service
    @Transactional(rollbackFor = Exception.class)
	@Override
	public int updateBoard(Board upBoard, String savePath, List<MultipartFile> images, boolean[] deleteImages) {
  	// images : 수정된 파일 리스트
    	
		int result = boardDAO.updateBoard(upBoard); // 게시글만 수정
		
	
			
		
		return result;
	}



	@Override
	public List<Attachment> selectFiles(int qnaNo) {
		return boardDAO.selectFiles(qnaNo);
	}





	@Override
	public PageInfo pagination(int cp, Search search) {
		
		// 1) 검색 조건에 맞는 전체 게시글 수 조회
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("search", search);
		
		  int searchListCount = boardDAO.getSerchListCount(map);
		  
		  pInfo.setPageInfo(cp, searchListCount);
				return pInfo;
	}




	@Override
	public List<Board> selectSerchList(PageInfo pInfo, Search search) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("search", search);
		
		return boardDAO.selectSearchList(pInfo, map);
	}



	// 메인 화면 list 얻어오기
	@Override
	public List<Board> mainBoardList() {
		return boardDAO.mainBoardList();
	}



	// 게시글 좋아요 서비스 구현
		@Transactional(rollbackFor = Exception.class)	
		@Override
		public int qnaBoardLike(int qnaNo, int memberNo) {

			int result = 0;
			// 좋아요 체크
			int chkResult = boardDAO.qnaLikeChk(qnaNo, memberNo);
			
			if(chkResult > 0) {
				// 체크가 되어있다면 삭제
				result = boardDAO.qnaLikeDelete(qnaNo, memberNo);
			} else {
				// 체크가 안되어있다면 추가
				result = boardDAO.qnaLikeAdd(qnaNo, memberNo);
			}
			
			return chkResult;
		}

		// 게시글 좋아요 갯수 서비스 구현
		@Override
		public int qnaLikeCount(int qnaNo) {
			int result = boardDAO.boardLikeCount(qnaNo);
			return result;
		}

		// 게시글 사용자 좋아요 여부 서비스 구현
		@Override
		public int qnaLikeUserChk(int qnaNo, int memberNo) {
			int result = boardDAO.qnaLikeChk(qnaNo, memberNo);
			return result;
		}
		

	
	
	

		@Override
		public int updateRcnt(int qnaNo) {
			
			int result = boardDAO.updateRcnt(qnaNo);
			return result;
		}
		
		
		// 게시글 신고 서비스 구현
		@Override
		public int reportBoard(int qnaNo) {
			return boardDAO.reportBoard(qnaNo);
		}

		//-----------------------------------------Summernote-----------------------------------------
				@Override
				public Map<String, String> insertImage(MultipartFile uploadFile, String savePath) {
					// 저장 폴더 선택
					File folder = new File(savePath);
					
					// 만약 폴더가 없을 경우 자동 생성 시키기
					if(!folder.exists())  folder.mkdir(); 
					Map<String, String> result = new HashMap<String, String>();
					
					// rename 작업
					String changeFileName = rename(uploadFile.getOriginalFilename());
							
					String filePath = "/resources/infoImages/";
					result.put("filePath", filePath);
					result.put("changeFileName", changeFileName);
					
					
					// transferTo() : 지정한 경로에 업로드된 파일정보를 실제 파일로 변환하는 메소드 -> 정상 호출 시 파일이 저장됨.
					try {
						uploadFile.transferTo(new File(savePath + "/" + changeFileName));
					} catch (Exception e) {
						e.printStackTrace();
					}
					return result;
				}
				
				
				
				// DB에 저장된 파일 목록 조회 Service 구현
				@Override
				public List<String> selectDbFileList() {
					return boardDAO.selectDbFileList();
				}
				
				//--------------------------------------------------------------------------------------------
				


}