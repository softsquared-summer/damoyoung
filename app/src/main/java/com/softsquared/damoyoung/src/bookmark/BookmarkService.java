package com.softsquared.damoyoung.src.bookmark;

import com.softsquared.damoyoung.src.bookmark.interfaces.BookmarkActivityView;
import com.softsquared.damoyoung.src.bookmark.interfaces.BookmarkRetrofitInterface;
import com.softsquared.damoyoung.src.bookmark.models.BookmarkResponse;
import com.softsquared.damoyoung.src.bookmark.models.BookmarkGetResponse;

import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.softsquared.damoyoung.src.ApplicationClass.MEDIA_TYPE_JSON;
import static com.softsquared.damoyoung.src.ApplicationClass.getRetrofit;

public class BookmarkService {

    private final BookmarkActivityView mBookmarkActivityView;

    public BookmarkService(final BookmarkActivityView bookmarkActivityView) {
        this.mBookmarkActivityView = bookmarkActivityView;
    }

    void postBookmark(JSONObject params) {

        final BookmarkRetrofitInterface bookmarkRetrofitInterface = getRetrofit().create(BookmarkRetrofitInterface.class);
        bookmarkRetrofitInterface.postBookmark(RequestBody.create(params.toString(), MEDIA_TYPE_JSON)).enqueue(new Callback<BookmarkResponse>() {
            @Override
            public void onResponse(Call<BookmarkResponse> call, Response<BookmarkResponse> response) {
                if (response == null) {
                    mBookmarkActivityView.validateMakeFailure("북마크 생성에 실패하였습니다.");
                    return;
                }
                final BookmarkResponse bookmarkResponse = response.body();

                if (bookmarkResponse == null) {
                    mBookmarkActivityView.validateMakeFailure(null);
                    return;
                } else if (bookmarkResponse.getCode() == 201) {
                    //유효하지 않는 토큰
                    mBookmarkActivityView.validateMakeFailure(bookmarkResponse.getMessage());
                } else if (bookmarkResponse.getCode() == 102) {
                    //북마크 이름을 안했을시
                    mBookmarkActivityView.validateMakeFailure(bookmarkResponse.getMessage());
                } else {
                    //북마크 등록 성공
                    mBookmarkActivityView.validateMakeSuccess(bookmarkResponse.getMessage());
                }

            }


            @Override
            public void onFailure(Call<BookmarkResponse> call, Throwable t) {
                mBookmarkActivityView.validateMakeFailure(null);
            }
        });


    }

    void getBookmark() {

        final BookmarkRetrofitInterface bookmarkRetrofitInterface = getRetrofit().create(BookmarkRetrofitInterface.class);
        bookmarkRetrofitInterface.getBookmark().enqueue(new Callback<BookmarkGetResponse>() {
            @Override
            public void onResponse(Call<BookmarkGetResponse> call, Response<BookmarkGetResponse> response) {
                if (response == null) {
                    mBookmarkActivityView.validateGetFailure("북마크 조회에 실패하였습니다.");
                    return;
                }
                final BookmarkGetResponse bookmarkGetResponse = response.body();

                if (bookmarkGetResponse == null) {
                    mBookmarkActivityView.validateGetFailure(null);
                    return;
                } else if (bookmarkGetResponse.getCode() == 201) {
                    //유효하지 않는 토큰
                    mBookmarkActivityView.validateGetFailure(bookmarkGetResponse.getMessage());
                } else {
                    //북마크 조회 성공
                    mBookmarkActivityView.validateGetSuccess(bookmarkGetResponse.getMessage(), bookmarkGetResponse.getBookmarkListItems());
                }

            }


            @Override
            public void onFailure(Call<BookmarkGetResponse> call, Throwable t) {
                mBookmarkActivityView.validateMakeFailure(null);
            }
        });


    }

    public void deleteBookmark(int bookmarkNo) {

        final BookmarkRetrofitInterface bookmarkRetrofitInterface = getRetrofit().create(BookmarkRetrofitInterface.class);
        bookmarkRetrofitInterface.deleteBookmark(bookmarkNo).enqueue(new Callback<BookmarkResponse>() {
            @Override
            public void onResponse(Call<BookmarkResponse> call, Response<BookmarkResponse> response) {
                if (response == null) {
                    mBookmarkActivityView.validateDeleteFailure("북마크 삭제에 실패하였습니다.");
                    return;
                }
                final BookmarkResponse bookmarkResponse = response.body();

                if (bookmarkResponse == null) {
                    mBookmarkActivityView.validateDeleteFailure(null);
                    return;
                } else if (bookmarkResponse.getCode() == 100) {
                    //북마크 삭제 성공
                    mBookmarkActivityView.validateDeleteSuccess(bookmarkResponse.getMessage());
                } else if (bookmarkResponse.getCode() == 201) {
                    //북마크 조회 성공
                    mBookmarkActivityView.validateDeleteFailure(bookmarkResponse.getMessage());
                } else {
                    mBookmarkActivityView.validateDeleteFailure(bookmarkResponse.getMessage());
                }

            }


            @Override
            public void onFailure(Call<BookmarkResponse> call, Throwable t) {
                mBookmarkActivityView.validateDeleteFailure(null);
            }
        });
    }

    void patchBookmark(int bookmarkNo, JSONObject params) {

        final BookmarkRetrofitInterface bookmarkRetrofitInterface = getRetrofit().create(BookmarkRetrofitInterface.class);
        bookmarkRetrofitInterface.modifyBookmark(bookmarkNo, RequestBody.create(params.toString(), MEDIA_TYPE_JSON)).enqueue(new Callback<BookmarkResponse>() {
            @Override
            public void onResponse(Call<BookmarkResponse> call, Response<BookmarkResponse> response) {
                if (response == null) {
                    mBookmarkActivityView.vaildateModifyFailure("북마크 생성에 실패하였습니다.");
                    return;
                }
                final BookmarkResponse bookmarkResponse = response.body();

                if (bookmarkResponse == null) {
                    mBookmarkActivityView.vaildateModifyFailure(null);
                    return;
                } else if (bookmarkResponse.getCode() == 100) {
                    //북마크 이름 수정 성공
                    mBookmarkActivityView.vaildateModifySuccess(bookmarkResponse.getMessage());
                } else if (bookmarkResponse.getCode() == 107) {
                    //북마크 번호 미입력시
                    mBookmarkActivityView.vaildateModifyFailure(bookmarkResponse.getMessage());
                } else if (bookmarkResponse.getCode() == 400) {
                    //유효한 북마크 번호 아닐시
                    mBookmarkActivityView.vaildateModifyFailure(bookmarkResponse.getMessage());
                } else {
                    mBookmarkActivityView.vaildateModifyFailure(bookmarkResponse.getMessage());
                }

            }


            @Override
            public void onFailure(Call<BookmarkResponse> call, Throwable t) {
                mBookmarkActivityView.vaildateModifyFailure(null);
            }
        });


    }
}
