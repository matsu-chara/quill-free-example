package matsu_chara.quill_free.repo

import matsu_chara.quill_free.Person
import matsu_chara.quill_free.quill.MyDbContext

class PersonRepository() {

  def insert(p: Person)(implicit ctx: MyDbContext): Unit = {
    import ctx._
    val q = quote {
      query[Person].insert(lift(p))
    }
    run(q)
    ()
  }

  def findById(id: Long)(implicit ctx: MyDbContext): Option[Person] = {
    import ctx._
    val q = quote {
      query[Person].filter(_.id == lift(id))
    }
    run(q).headOption
  }

  def deleteAll()(implicit ctx: MyDbContext): Unit = {
    import ctx._
    val q = quote {
      query[Person].delete
    }
    run(q)
    ()
  }
}
