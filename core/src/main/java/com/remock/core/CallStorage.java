package com.remock.core;

public interface CallStorage {

  ReMockCallList getCallList();
  void clear();

  void add(ReMockCall reMockCall);
}
