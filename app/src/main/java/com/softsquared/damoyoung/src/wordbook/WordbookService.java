package com.softsquared.damoyoung.src.wordbook;

import com.softsquared.damoyoung.src.bookmark.interfaces.BookmarkRetrofitInterface;
import com.softsquared.damoyoung.src.bookmark.models.BookmarkGetResponse;
import com.softsquared.damoyoung.src.wordbook.interfaces.WordbookActivityView;
import com.softsquared.damoyoung.src.wordbook.interfaces.WordbookRetrofitInterface;
import com.softsquared.damoyoung.src.wordbook.models.WordbookResponse;

import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.softsquared.damoyoung.src.ApplicationClass.MEDIA_TYPE_JSON;
import static com.softsquared.damoyoung.src.ApplicationClass.getRetrofit;

public class WordbookService {

    private final WordbookActivityView mWordbookActivityView;

    WordbookService(final WordbookActivityView wordbookActivityView) {
        this.mWordbookActivityView = wordbookActivityView;
    }

    void getWordbook(int bookmarkNo,JSONObject params) {

        final WordbookRetrofitInterface wordbookRetrofitInterface = getRetrofit().create(WordbookRetrofitInterface.class);
        wordbookRetrofitInterface.getWordbook(bookmarkNo,RequestBody.create(params.toString(), MEDIA_TYPE_JSON)).enqueue(new Callback<WordbookResponse>() {
            @Override
            public void onResponse(Call<WordbookResponse> call, Response<WordbookResponse> response) {
                if (response == null) {
                    mWordbookActivityView.validateGetFailure("북마크 조회에 실패하였습니다.");
                    return;
                }
                final WordbookResponse wordbookResponse = response.body();

                if (wordbookResponse == null) {
                    mWordbookActivityView.validateGetFailure(null);
                    return;
                } else if (wordbookResponse.getCode() == 201) {
                    //유효하지 않는 토큰
                    mWordbookActivityView.validateGetFailure(wordbookResponse.getMessage());
                }else {
                    //단어장 조회 성공
                    mWordbookActivityView.validateGetSuccess(wordbookResponse.getMessage(),wordbookResponse.getWordBookExListItems());
                }

            }


            @Override
            public void onFailure(Call<WordbookResponse> call, Throwable t) {
                mWordbookActivityView.validateGetFailure("바로실패");

            }
        });


    }

    void deleteWord(int bookmarkNo,JSONObject params) {

        final WordbookRetrofitInterface wordbookRetrofitInterface = getRetrofit().create(WordbookRetrofitInterface.class);
        wordbookRetrofitInterface.deleteWord(bookmarkNo,RequestBody.create(params.toString(), MEDIA_TYPE_JSON)).enqueue(new Callback<WordbookResponse>() {
            @Override
            public void onResponse(Call<WordbookResponse> call, Response<WordbookResponse> response) {
                if (response == null) {
                    mWordbookActivityView.validateDeleteFailure("단어 삭제를 실패하였습니다.");
                    return;
                }
                final WordbookResponse wordbookResponse = response.body();

                if (wordbookResponse == null) {
                    mWordbookActivityView.validateDeleteFailure(null);
                    return;
                } else if (wordbookResponse.getCode() == 100) {
                    //단어 삭제 성공
                    mWordbookActivityView.validateDeleteSuccess(wordbookResponse.getMessage());

                } else if (wordbookResponse.getCode() == 201) {
                    //유효하지 않는 토큰
                    mWordbookActivityView.validateDeleteFailure(wordbookResponse.getMessage());

                }else {
                    mWordbookActivityView.validateDeleteFailure(wordbookResponse.getMessage());

                }

            }


            @Override
            public void onFailure(Call<WordbookResponse> call, Throwable t) {
                mWordbookActivityView.validateDeleteFailure("네트워크가 원활하지 않습니다.");

            }
        });

    }

    void deleteSentence(int bookmarkNo,int sentenceNo) {

        final WordbookRetrofitInterface wordbookRetrofitInterface = getRetrofit().create(WordbookRetrofitInterface.class);
        wordbookRetrofitInterface.deleteSentence(bookmarkNo,sentenceNo).enqueue(new Callback<WordbookResponse>() {
            @Override
            public void onResponse(Call<WordbookResponse> call, Response<WordbookResponse> response) {
                if (response == null) {
                    mWordbookActivityView.vaildateSentenceDeleteFailure("예문 삭제 실패.");
                    return;
                }
                final WordbookResponse wordbookResponse = response.body();

                if (wordbookResponse == null) {
                    mWordbookActivityView.vaildateSentenceDeleteFailure(null);
                    return;
                } else if (wordbookResponse.getCode() == 100) {
                    //예문 삭제 성공
                    mWordbookActivityView.vaildateSentenceDeleteSuccess(wordbookResponse.getMessage());

                } else if (wordbookResponse.getCode() == 115) {
                    //유효하지 않는 토큰
                    mWordbookActivityView.vaildateSentenceDeleteFailure(wordbookResponse.getMessage());

                }else if (wordbookResponse.getCode() == 116) {
                    //유효하지 않는 토큰
                    mWordbookActivityView.vaildateSentenceDeleteFailure(wordbookResponse.getMessage());

                }else if (wordbookResponse.getCode() == 300) {
                    //유효하지 않는 토큰
                    mWordbookActivityView.vaildateSentenceDeleteFailure(wordbookResponse.getMessage());

                }else if (wordbookResponse.getCode() == 301) {
                    //유효하지 않는 토큰
                    mWordbookActivityView.vaildateSentenceDeleteFailure(wordbookResponse.getMessage());

                }else if (wordbookResponse.getCode() == 400) {
                    //유효하지 않는 토큰
                    mWordbookActivityView.vaildateSentenceDeleteFailure(wordbookResponse.getMessage());

                }else if (wordbookResponse.getCode() == 401) {
                    //유효하지 않는 토큰
                    mWordbookActivityView.vaildateSentenceDeleteFailure(wordbookResponse.getMessage());

                }else {
                    System.out.println("여긴뭐냐");
                    mWordbookActivityView.vaildateSentenceDeleteFailure(wordbookResponse.getMessage());

                }

            }


            @Override
            public void onFailure(Call<WordbookResponse> call, Throwable t) {
                mWordbookActivityView.validateDeleteFailure("네트워크가 원활하지 않습니다.");

            }
        });


    }
}
