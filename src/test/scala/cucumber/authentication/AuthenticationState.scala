package cucumber.authentication

object AuthenticationState {

  type User = String

  private[this] var _currentUser: Option[User] = None

  def currentUser = _currentUser

  private[authentication] def login(user: User): Unit = {
    _currentUser = Some(user)
  }

  private[authentication] def logout(): Unit = {
    _currentUser = None
  }
}
