import co.technius.scalajs.mithril._
import org.scalatest._
import org.scalajs.dom
import scala.scalajs.js

class MithrilTest extends FlatSpec with Matchers with TestUtils {

  "The facade" should "work" in {
    // if the mithril functions don't work, this test will fail
    m.version shouldNot be (js.undefined)


    val comp = Component.viewOnly[js.Object] { vnode =>
      m("div", "test")
    }
    mountApp(comp)
  }

  it should "mount components successfully" in {
    val comp = Component.viewOnly[js.Object] { vnode =>
      m("div", js.Array(
        m("span", js.Dynamic.literal(id = "should-appear"))
      ))
    }
    mountApp(comp)
    dom.document.getElementById("should-appear") shouldNot be (null)
  }

  it should "support streams" in {
    val s = MithrilStream[Int](5)
    s() shouldBe (5)
    val child: MStream[Int] = s.map((_: Int) + 5)
    child() shouldBe (10)
    s() = 10
    child() shouldBe(15)

    val nums = MithrilStream[Int](1)
    val sum = nums.fold(0)(_ + _)
    sum() shouldBe (1)
    nums() = 2
    sum() shouldBe (3)
    nums() = 3
    sum() shouldBe (6)

    val emptyStream = MithrilStream[String]()
    emptyStream.toOption shouldBe (None)
  }

  it should "ensure stream type signatures are sound" in {
    """
    val stream = MithrilStream[String]("foo")
    val stream2: MStream[Any] = stream
    stream2() = 5
    """.stripMargin shouldNot typeCheck
  }
}
