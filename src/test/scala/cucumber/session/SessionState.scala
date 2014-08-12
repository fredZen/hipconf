package cucumber.session

private[session] object SessionState {
  private[this] var _sessionUnderEdit: Option[Session] = None
  def sessionUnderEdit = _sessionUnderEdit
  def editing(session: Session): Unit = {
    _sessionUnderEdit = Some(session)
  }

  private[this] var _publishedSessions = List[Session]()
  def publishedSessions = _publishedSessions
  def publishCurrentSession(): Unit = {
    _publishedSessions = sessionUnderEdit.get :: _publishedSessions
    _sessionUnderEdit = None
  }
}
