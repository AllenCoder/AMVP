package com.github.rubensousa.amvp;

import com.github.rubensousa.amvp.interactor.AbstractPresenterInteractor;
import com.github.rubensousa.amvp.interactor.MvpInteractor;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;


public class PresenterTest {

    @Mock
    private MvpView mView;

    @Mock
    private MvpInteractor mInteractor;

    private AbstractPresenter<MvpView> mPresenter;

    private AbstractPresenterInteractor<MvpView, MvpInteractor> mPresenterInteractor;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        mPresenter = new AbstractPresenter<MvpView>() {

        };

        mPresenterInteractor = new AbstractPresenterInteractor<MvpView, MvpInteractor>() {

            @Override
            public MvpInteractor createInteractor() {
                return mInteractor;
            }
        };
    }

    @Test
    public void viewAttach() {
        mPresenter.onViewAttach(mView);
        assertTrue(mPresenter.isViewAttached());
    }

    @Test
    public void viewDetach() {
        mPresenter.onViewAttach(mView);
        mPresenter.onViewDetach();
        assertTrue(!mPresenter.isViewAttached());
    }

    @Test
    public void interactorCreation(){
        assertTrue(mPresenterInteractor.getInteractor() != null);
    }

    @Test
    public void destroy() {
        mPresenterInteractor.onDestroy();
        Mockito.verify(mInteractor).onDestroy();
    }
}