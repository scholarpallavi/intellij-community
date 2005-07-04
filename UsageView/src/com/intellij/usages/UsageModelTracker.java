package com.intellij.usages;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiTreeChangeEvent;
import com.intellij.psi.PsiTreeChangeListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: max
 * Date: Dec 16, 2004
 * Time: 4:46:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class UsageModelTracker {
  private PsiTreeChangeListener myPsiListener;

  public interface UsageModelTrackerListener {
    public void modelChanged(boolean isPropertyChange);
  }

  private Project myProject;
  private List<UsageModelTrackerListener> myListeners = new ArrayList<UsageModelTrackerListener>();

  public UsageModelTracker(Project project) {
    myProject = project;
    myPsiListener = new PsiTreeChangeListener() {
      public void beforeChildAddition(PsiTreeChangeEvent event) {
      }

      public void beforeChildRemoval(PsiTreeChangeEvent event) {
      }

      public void beforeChildReplacement(PsiTreeChangeEvent event) {
      }

      public void beforeChildMovement(PsiTreeChangeEvent event) {
      }

      public void beforeChildrenChange(PsiTreeChangeEvent event) {
      }

      public void beforePropertyChange(PsiTreeChangeEvent event) {
      }

      public void childAdded(PsiTreeChangeEvent event) {
        fireModelChanged(false);
      }

      public void childRemoved(PsiTreeChangeEvent event) {
        fireModelChanged(false);
      }

      public void childReplaced(PsiTreeChangeEvent event) {
        fireModelChanged(false);
      }

      public void childrenChanged(PsiTreeChangeEvent event) {
        fireModelChanged(false);
      }

      public void childMoved(PsiTreeChangeEvent event) {
        fireModelChanged(false);
      }

      public void propertyChanged(PsiTreeChangeEvent event) {
        fireModelChanged(true);
      }
    };
    PsiManager.getInstance(project).addPsiTreeChangeListener(myPsiListener);
  }

  public void dispose() {
    PsiManager.getInstance(myProject).removePsiTreeChangeListener(myPsiListener);
  }

  public void addListener(UsageModelTrackerListener listener) {
    myListeners.add(listener);
  }

  public void removeListener(UsageModelTrackerListener listener) {
    myListeners.remove(listener);
  }

  private void fireModelChanged(final boolean isPropertyChange) {
    final UsageModelTrackerListener[] listeners = myListeners.toArray(new UsageModelTrackerListener[myListeners.size()]);
    for (UsageModelTrackerListener listener : listeners) {
      listener.modelChanged(isPropertyChange);
    }
  }
}
