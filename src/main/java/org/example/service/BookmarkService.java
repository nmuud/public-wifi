package org.example.service;

import org.example.dto.BookmarkDTO;
import org.example.dto.BookmarkGroupDTO;
import org.example.entity.BookmarkGroup;
import org.example.repository.BookmarkRepository;

import java.util.ArrayList;

public class BookmarkService {
    BookmarkRepository bookmarkRepository = new BookmarkRepository();

    /**
     * 북마크 추가
     *
     * @param id    그룹 ID
     * @param mgrNo 와이파이 관리 번호
     * @return 추가 성공 여부
     */
    public boolean createBookmark(int id, String mgrNo) {
        return bookmarkRepository.createBookmark(id, mgrNo);
    }

    /**
     * 전체 북마크 조회
     *
     * @return 북마크 목록
     */
    public ArrayList<BookmarkDTO> getBookmarkList() {
        return bookmarkRepository.getBookmarkList();
    }

    /**
     * 북마크 조회
     *
     * @param id 북마크 ID
     * @return 북마크 정보
     */
    public BookmarkDTO getBookmarkInfo(int id) {
        return bookmarkRepository.getBookmarkInfo(id);
    }

    /**
     * 북마크 삭제
     *
     * @param id 북마크 ID
     * @return 삭제 성공 여부
     */
    public boolean deleteBookmark(int id) {
        return bookmarkRepository.deleteBookmark(id);
    }

    /**
     * 북마크 그룹 추가
     *
     * @param name     북마크 그룹 이름
     * @param sequence 그룹 순서
     * @return 추가 성공 여부
     */
    public boolean createBookmarkGroup(String name, int sequence) {
        return bookmarkRepository.createBookmarkGroup(name, sequence);
    }

    /**
     * 전체 북마크 그룹 조회
     *
     * @return 북마크 그룹 목록
     */
    public ArrayList<BookmarkGroup> getBookmarkGroupList() {
        return bookmarkRepository.getBookmarkGroupList();
    }

    /**
     * 북마크 그룹 조회
     *
     * @param id 북마크 그룹 ID
     * @return 북마크 그룹 정보
     */
    public BookmarkGroupDTO getBookmarkGroupInfo(int id) {
        return bookmarkRepository.getBookmarkGroupInfo(id);
    }

    /**
     * 북마크 그룹 수정
     *
     * @param bookmarkGroupName 그룹 이름
     * @param bookmarkGroupSeq  그룹 순서
     * @param id                그룹 ID
     * @return 수정 성공 여부
     */
    public boolean updateBookmarkGroup(String bookmarkGroupName, int bookmarkGroupSeq, int id) {
        return bookmarkRepository.updateBookmarkGroup(bookmarkGroupName, bookmarkGroupSeq, id);
    }

    /**
     * 북마크 그룹 삭제
     *
     * @param id 그룹 ID
     * @return 삭제 성공 여부
     */
    public boolean deleteBookmarkGroup(int id) {
        return bookmarkRepository.deleteBookmarkGroup(id);
    }
}
